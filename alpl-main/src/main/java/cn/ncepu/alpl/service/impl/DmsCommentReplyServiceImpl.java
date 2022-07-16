package cn.ncepu.alpl.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.ResultEnum;
import cn.ncepu.alpl.dao.DmsCommentDao;
import cn.ncepu.alpl.dao.DmsCommentReplyDao;
import cn.ncepu.alpl.domain.DmsCommentReplyDetail;
import cn.ncepu.alpl.domain.UmsUserDetail;
import cn.ncepu.alpl.exception.CustomRuntimeException;
import cn.ncepu.alpl.mapper.DmsCommentReplyMapper;
import cn.ncepu.alpl.mapper.DmsUserCommentReplyStarsRelationMapper;
import cn.ncepu.alpl.model.*;
import cn.ncepu.alpl.repostory.DmsCommentReplyCacheService;
import cn.ncepu.alpl.service.DmsCommentReplyService;
import cn.ncepu.alpl.service.DmsCommentService;
import cn.ncepu.alpl.service.UmsUserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cn.ncepu.alpl.constant.CommentHighLightRoleName.TEACHER;
import static cn.ncepu.alpl.constant.CommentHighLightRoleName.TUTOR;

/**
 *
 * @author xufuhang
 */
@Service
public class DmsCommentReplyServiceImpl implements DmsCommentReplyService{

    @Autowired
    DmsCommentReplyMapper commentReplyMapper;
    @Autowired
    DmsCommentReplyDao commentReplyDao;
    @Autowired
    DmsCommentService commentService;
    @Autowired
    UmsUserService userService;
    @Autowired
    DmsCommentDao commentDao;
    @Autowired
    DmsCommentReplyCacheService commentReplyCacheService;
    @Autowired
    DmsUserCommentReplyStarsRelationMapper userCommentReplyStarsRelationMapper;

    @Override
    public int create(DmsCommentReply commentReply, Principal principal) {
        String username = principal.getName();
        UmsUserDetail userDetail = userService.getUserDetailByUsername(username);

        if (userDetail.getIsForbiddenWords() != null && userDetail.getIsForbiddenWords() == 1) {
            throw new CustomRuntimeException("您已被禁言");
        }

        // 获取当前用户角色类型，若是教师和助教则赋值给comment，前端高亮显示角色名称
        String fromUserRoleName = getFromUserRoleName(userDetail);
        commentReply.setFromUserRoleName(fromUserRoleName);

        commentReply.setFromUserId(userDetail.getId());
        commentReply.setCreateTime(new Date());
        int create = commentReplyMapper.insertSelective(commentReply);
        if (create > 0) {
            int incr = commentService.incrCommentReplyCount(commentReply.getCommentId(), 1);
        }

        commentReplyCacheService.delCommentReplyDetailByCommentId(commentReply.getCommentId());
        return create;
    }

    @Override
    public int delete(Integer commentReplyId) {
        DmsCommentReply commentReply = commentReplyMapper.selectByPrimaryKey(commentReplyId);

        int delete = commentReplyMapper.deleteByPrimaryKey(commentReplyId);
        if (delete > 0) {
            commentService.incrCommentReplyCount(commentReply.getCommentId(), -1);
        }

        commentReplyCacheService.delCommentReplyDetailByCommentId(commentReply.getCommentId());
        return delete;
    }

    @Override
    public int deleteSelf(Integer commentReplyId, Principal principal) {
        String username = principal.getName();
        UmsUser user = userService.getUserByUsername(username);
        DmsCommentReply commentReply = commentReplyMapper.selectByPrimaryKey(commentReplyId);

        int delete = commentReplyDao.deleteByIdAndFromUserId(commentReplyId, user.getId());
        if (delete > 0) {
            commentService.incrCommentReplyCount(commentReply.getCommentId(), -1);
        }

        commentReplyCacheService.delCommentReplyDetailByCommentId(commentReply.getCommentId());
        return delete;
    }

    @Override
    public CommonPage<DmsCommentReply> fetchList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DmsCommentReply> commentReplyList = commentReplyMapper.selectByExample(null);
        CommonPage<DmsCommentReply> page = CommonPage.genCommonPage(commentReplyList);
        return page;
    }

    @Override
    public CommonPage<DmsCommentReplyDetail> selectPageBySectionId(Integer commentId,
                                                                   Integer pageNum,
                                                                   Integer pageSize,
                                                                   Boolean sortByStars,
                                                                   Boolean sortByCreateTime,
                                                                   Principal principal) {
        CacheKey<Integer> cacheKey = new CacheKey();
        cacheKey.setKey(commentId);
        cacheKey.setPageNum(pageNum);
        cacheKey.setPageSize(pageSize);
        cacheKey.setSortType(sortByStars ? 1 : (sortByCreateTime ? 2 : 0));
        CommonPage<DmsCommentReplyDetail> page = commentReplyCacheService.getCommentReplyDetailPage(cacheKey);
        if (page == null) {
            PageHelper.startPage(pageNum, pageSize);
            List<DmsCommentReplyDetail> replyDetails =
                    commentReplyDao.selectByCommentId(commentId, sortByStars, sortByCreateTime);
            page = CommonPage.genCommonPage(replyDetails);
            commentReplyCacheService.setCommentReplyDetailPage(cacheKey, page);
        }

        // 对分页结果进行处理，查询用户已点赞的评论
        if (principal == null) {
            return page;
        }
        Set<Integer> commentIdSet = getStarsCommentReplyIdSetByUsername(principal);
        for (DmsCommentReplyDetail replyDetail : page.getList()) {
            if (commentIdSet.contains(replyDetail.getId())) {
                replyDetail.setIsStars(true);
            }
        }
        return page;
    }

    @Override
    public int update(DmsCommentReply reply) {
        int count = commentReplyMapper.updateByPrimaryKeySelective(reply);

        queryCommentIdAndDelCache(reply);
        return count;
    }

    @Override
    public int stars(DmsCommentReply reply, Integer count, Principal principal) {
        if (principal == null) {
            throw new CustomRuntimeException(ResultEnum.UNAUTHORIZED.getMessage());
        }
        String username = principal.getName();
        UmsUser user = userService.getUserByUsername(username);

        DmsUserCommentReplyStarsRelation record = new DmsUserCommentReplyStarsRelation();
        record.setUserId(user.getId());
        record.setCommentReplyId(reply.getId());
        int update;
        if (count > 0) {
            // 点赞
            update = userCommentReplyStarsRelationMapper.insert(record);
        } else {
            // 取消点赞
            DmsUserCommentReplyStarsRelationExample example = new DmsUserCommentReplyStarsRelationExample();
            example.createCriteria()
                    .andCommentReplyIdEqualTo(record.getCommentReplyId())
                    .andUserIdEqualTo(record.getUserId());
            update = userCommentReplyStarsRelationMapper.deleteByExample(example);
        }
        int incr = commentReplyDao.updateIncrStars(reply.getId(), count);

        queryCommentIdAndDelCache(reply);
        commentReplyCacheService.delUserCommentReplyStars(username);
        return update;
    }

    @Override
    public int topping(Integer id) {
        DmsCommentReply reply = commentReplyMapper.selectByPrimaryKey(id);
        commentReplyDao.selectByCommentIdForUpdate(reply.getCommentId());

        int maxToppingSort = commentReplyDao.selectMaxToppingSortByCommentId(reply.getCommentId());
        short nextToppingSort = (short) (maxToppingSort + 1);
        reply.setToppingSort(nextToppingSort);
        int count = commentReplyMapper.updateByPrimaryKeySelective(reply);

        commentReplyCacheService.delCommentReplyDetailByCommentId(reply.getCommentId());
        return count;
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

    private Set<Integer> getStarsCommentReplyIdSetByUsername(Principal principal) {
        String username = principal.getName();
        Set<Integer> commentIdSet = commentReplyCacheService.getStarsCommentReplyIdSetByUsername(username);
        if (!CollectionUtil.isEmpty(commentIdSet)) {
            return commentIdSet;
        }

        commentIdSet = new HashSet<>();
        UmsUser user = userService.getUserByUsername(username);
        DmsUserCommentReplyStarsRelationExample example = new DmsUserCommentReplyStarsRelationExample();
        example.createCriteria().andUserIdEqualTo(user.getId());
        List<DmsUserCommentReplyStarsRelation> relationList = userCommentReplyStarsRelationMapper.selectByExample(example);
        for (DmsUserCommentReplyStarsRelation relation : relationList) {
            Integer replyId = relation.getCommentReplyId();
            commentIdSet.add(replyId);
        }
        commentReplyCacheService.setStarsCommentReplyIdByUsername(username, commentIdSet);
        return commentIdSet;
    }

    private void queryCommentIdAndDelCache(DmsCommentReply reply) {
        reply = commentReplyMapper.selectByPrimaryKey(reply.getId());
        commentReplyCacheService.delCommentReplyDetailByCommentId(reply.getCommentId());
    }

}




