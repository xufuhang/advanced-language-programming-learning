package cn.ncepu.alpl.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.api.ResultEnum;
import cn.ncepu.alpl.dao.DmsCommentDao;
import cn.ncepu.alpl.domain.DmsCommentDetail;
import cn.ncepu.alpl.domain.UmsUserDetail;
import cn.ncepu.alpl.exception.CustomRuntimeException;
import cn.ncepu.alpl.mapper.DmsCommentMapper;
import cn.ncepu.alpl.mapper.DmsUserCommentStarsRelationMapper;
import cn.ncepu.alpl.model.*;
import cn.ncepu.alpl.repostory.DmsCommentCacheService;
import cn.ncepu.alpl.service.CmsChapterService;
import cn.ncepu.alpl.service.CmsSectionService;
import cn.ncepu.alpl.service.DmsCommentService;
import cn.ncepu.alpl.service.UmsUserService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static cn.ncepu.alpl.constant.CommentHighLightRoleName.TEACHER;
import static cn.ncepu.alpl.constant.CommentHighLightRoleName.TUTOR;

/**
@author xufuhang
@date 2022/3/29-15:26
*/
@Service
@Slf4j
public class DmsCommentServiceImpl implements DmsCommentService {

    @Autowired
    DmsCommentMapper commentMapper;
    @Autowired
    DmsCommentDao commentDao;
    @Autowired
    DmsUserCommentStarsRelationMapper userCommentStarsRelationMapper;
    @Autowired
    UmsUserService userService;
    @Autowired
    CmsSectionService sectionService;
    @Autowired
    CmsChapterService chapterService;
    @Autowired
    DmsCommentCacheService commentCacheService;

    @Override
    public int create(DmsComment comment, Principal principal) {
        String username = principal.getName();
        UmsUserDetail userDetail = userService.getUserDetailByUsername(username);

        if (userDetail.getIsForbiddenWords() != null && userDetail.getIsForbiddenWords() == 1) {
            throw new CustomRuntimeException("您已被禁言");
        }

        // 获取当前用户角色类型，若是教师和助教则赋值给comment，前端高亮显示角色名称
        String fromUserRoleName = getFromUserRoleName(userDetail);
        comment.setFromUserRoleName(fromUserRoleName);

        comment.setCreateTime(new Date());
        comment.setFromUserId(userDetail.getId());
        int insert = commentMapper.insertSelective(comment);

        if (insert > 0) {
            sectionService.incrNewCommentCount(comment.getSectionId());
        }
        /*
        插入记录时，需要删除的缓存包括，被该记录参考的表的相关数据，
        比如comment参考section表，section参考chapter表
         */
        commentCacheService.delCommentDetailBySectionId(comment.getSectionId());
        return insert;
    }

    private String getFromUserRoleName(UmsUserDetail userDetail) {
        String fromUserRoleName = null;
        for (UmsRole role : userDetail.getRoleList()) {
            if (role.getName().equals(TUTOR)) {
                fromUserRoleName = TUTOR;
            }
            if (role.getName().equals(TEACHER)) {
                fromUserRoleName = TEACHER;
                break;
            }
        }
        return fromUserRoleName;
    }


    /**
     * 该方法无需访问权限，用于删除用户自己发布的评论。为何要验证是否是当前用户发布的？
     * 因为用户可能伪造请求，如果不验证，则其他用户也可以请求此方法并删除任何评论
     * @param commentId 评论id
     * @param principal 当前用户
     * @return 修改行数
     */
    @Override
    public int deleteSelf(Integer commentId, Principal principal) {
        // 先获取到sectionId再删除记录
        DmsComment comment = commentMapper.selectByPrimaryKey(commentId);

        String username = principal.getName();
        UmsUser user = userService.getUserByUsername(username);
        int count1 = incrSectionCommentCount(commentId, -1);
        if (count1 > 0) {
            count1 = commentDao.deleteByIdAndFromUserId(commentId, user.getId());
        }
        int count = count1;
        /*
        更新、删除记录时，需要删除的缓存包括，当前记录以及被参考表相关数据
         */
        commentCacheService.delCommentDetailBySectionId(comment.getSectionId());
        return count;
    }

    @Override
    public int deleteById(Integer commentId) {
        DmsComment comment = commentMapper.selectByPrimaryKey(commentId);

        incrSectionCommentCount(commentId, -1);
        int delete = commentMapper.deleteByPrimaryKey(commentId);

        commentCacheService.delCommentDetailBySectionId(comment.getSectionId());
        return delete;
    }

    @Override
    public CommonPage<DmsComment> fetchList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DmsComment> commentList = commentMapper.selectByExample(null);
        CommonPage<DmsComment> page = CommonPage.genCommonPage(commentList);
        return page;
    }

    @Override
    public CommonPage<DmsCommentDetail> fetchDetailList(Principal principal,
                                                        Integer sectionId,
                                                        Integer pageNum,
                                                        Integer pageSize,
                                                        Boolean sortByStars,
                                                        Boolean sortByCreateTime) {
        CacheKey<Integer> cacheKey = new CacheKey();
        cacheKey.setKey(sectionId);
        cacheKey.setPageNum(pageNum);
        cacheKey.setPageSize(pageSize);
        cacheKey.setSortType(sortByStars ? 1 : (sortByCreateTime ? 2 : 0));
        CommonPage<DmsCommentDetail> page = commentCacheService.getCommentDetailPage(cacheKey);
        if (page == null) {
            PageHelper.startPage(pageNum, pageSize);
            List<DmsCommentDetail> commentDetailList =
                    commentDao.selectDetailBySectionId(sectionId, sortByStars, sortByCreateTime);
            page = CommonPage.genCommonPage(commentDetailList);
            commentCacheService.setCommentDetailPage(cacheKey, page);
        }

        // 对分页结果进行处理，查询用户已点赞的评论
        if (principal == null) {
            return page;
        }
        Set<Integer> commentIdSet = getStarsCommentIdSetByUsername(principal);
        for (DmsCommentDetail commentDetail : page.getList()) {
            if (commentIdSet.contains(commentDetail.getId())) {
                commentDetail.setIsStars(true);
            }
        }
        return page;
    }

    @Override
    public int incrCommentReplyCount(Integer commentId, int count) {
        int incr = commentDao.updateIncrCommentReplyCount(commentId, count);

        querySectionIdAndDelCache(commentId);
        return incr;
    }

    @Override
    public int update(DmsComment comment) {
        int count = commentMapper.updateByPrimaryKeySelective(comment);

        querySectionIdAndDelCache(comment.getId());
        return count;
    }

    @Override
    public int stars(DmsComment comment, Integer count, Principal principal) {
        if (principal == null) {
            throw new CustomRuntimeException(ResultEnum.UNAUTHORIZED.getMessage());
        }
        String username = principal.getName();
        UmsUser user = userService.getUserByUsername(username);

        DmsUserCommentStarsRelation record = new DmsUserCommentStarsRelation();
        record.setUserId(user.getId());
        record.setCommentId(comment.getId());
        int update;
        if (count > 0) {
            update = userCommentStarsRelationMapper.insert(record);
        } else {
            DmsUserCommentStarsRelationExample example = new DmsUserCommentStarsRelationExample();
            example.createCriteria()
                    .andCommentIdEqualTo(record.getCommentId())
                    .andUserIdEqualTo(record.getUserId());
            update = userCommentStarsRelationMapper.deleteByExample(example);
        }

        int incr = commentDao.updateIncrStars(comment.getId(), count);

        querySectionIdAndDelCache(comment.getId());
        commentCacheService.delUserCommentStars(username);
        return update;
    }

    @Override
    public int pinned(Integer id) {
        DmsComment comment = commentMapper.selectByPrimaryKey(id);
        // 锁定小节记录
        commentDao.selectBySectionIdForUpdate(comment.getSectionId());

        // 获取下一个置顶排序序号
        int maxToppingSort = commentDao.selectMaxToppingSortBySectionId(comment.getSectionId());
        short nextToppingSort = (short) (maxToppingSort + 1);

        // 修改记录
        comment.setToppingSort(nextToppingSort);
        int count = commentMapper.updateByPrimaryKeySelective(comment);

        // 清除缓存
        commentCacheService.delCommentDetailBySectionId(comment.getSectionId());
        return count;
    }

    private int incrSectionCommentCount(Integer commentId, int count) {
        DmsComment comment = commentMapper.selectByPrimaryKey(commentId);
        int incr = sectionService.incrCommentCount(comment.getSectionId(), count);
        return incr;
    }

    private Set<Integer> getStarsCommentIdSetByUsername(Principal principal) {
        String username = principal.getName();
        Set<Integer> commentIdSet = commentCacheService.getStarsCommentIdSetByUsername(username);
        if (!CollectionUtil.isEmpty(commentIdSet)) {
            return commentIdSet;
        }

        commentIdSet = userService.getStarsCommentIdSetByUsername(username);
        commentCacheService.setStarsCommentIdByUsername(username, commentIdSet);
        return commentIdSet;
    }

    private void querySectionIdAndDelCache(Integer commentId) {
        DmsComment comment = commentMapper.selectByPrimaryKey(commentId);
        commentCacheService.delCommentDetailBySectionId(comment.getSectionId());
    }

}
