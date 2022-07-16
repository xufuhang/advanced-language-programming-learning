package cn.ncepu.alpl.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.dao.CmsChapterDao;
import cn.ncepu.alpl.dao.CmsModuleDao;
import cn.ncepu.alpl.domain.CmsChapterDetail;
import cn.ncepu.alpl.mapper.CmsChapterMapper;
import cn.ncepu.alpl.model.CmsChapter;
import cn.ncepu.alpl.model.UmsUser;
import cn.ncepu.alpl.repostory.CmsChapterCacheService;
import cn.ncepu.alpl.service.CmsChapterService;
import cn.ncepu.alpl.service.UmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;


/**
@author xufuhang
@date 2022/3/13-21:41
*/
@Service
@Slf4j
public class CmsChapterServiceImpl
    implements CmsChapterService{

    @Autowired
    CmsChapterDao chapterDao;
    @Autowired
    CmsChapterMapper chapterMapper;
    @Autowired
    CmsModuleDao cmsModuleDao;
    @Autowired
    UmsUserService userService;
    @Autowired
    CmsChapterCacheService chapterCacheService;

    @Override
    public int create(CmsChapter chapter, Principal principal) {
        String username = principal.getName();
        UmsUser user = userService.getUserByUsername(username);
        chapter.setCreateUserId(user.getId());

        Integer moduleId = chapter.getModuleId();
        int nextSort = getNextSort(moduleId);

        chapter.setSort((short) nextSort);
        chapterMapper.insertSelective(chapter);

        chapterCacheService.delChapterByModuleId(chapter.getModuleId());
        return 1;
    }

    @Override
    public int delete(Integer chapterId) {
        CmsChapter chapter = chapterMapper.selectByPrimaryKey(chapterId);
        int delete = chapterMapper.deleteByPrimaryKey(chapterId);

        chapterCacheService.delChapterByModuleId(chapter.getModuleId());
        return delete;
    }

    @Override
    public List<CmsChapterDetail> queryChapterDetailListByModuleId(Integer moduleId) {
        List<CmsChapterDetail> chapterDetailList = chapterCacheService.getChapterDetailList(moduleId);
        if (!CollectionUtil.isEmpty(chapterDetailList)) {
            return chapterDetailList;
        }

        chapterDetailList = cmsModuleDao.selectDetailById(moduleId);

        chapterCacheService.setChapterDetailList(moduleId, chapterDetailList);
        return chapterDetailList;
    }

    @Override
    public List<CmsChapter> queryByCondition(CmsChapter chapter) {
        CacheKey<CmsChapter> cacheKey = new CacheKey<>();
        cacheKey.setKey(chapter);
        List<CmsChapter> chapterList = chapterCacheService.getChapterList(cacheKey);
        if (chapterList != null) {
            return chapterList;
        }

        chapterList = chapterDao.selectByCondition(chapter);

        chapterCacheService.setChapterList(cacheKey, chapterList);
        return chapterList;
    }

    @Override
    public int updatePosition(CmsChapter chapter) {
        if (chapter.getSort() == null) {
            short nextSort = getNextSort(chapter.getModuleId());
            chapter.setSort(nextSort);
        } else {
            chapterDao.updateSortGreaterThanTheNum(chapter);
        }
        int move = chapterMapper.updateByPrimaryKeySelective(chapter);

        queryModuleIdAndDelCache(chapter.getId());
        return move;
    }

    @Override
    public int updateByIdSelective(CmsChapter chapter) {
        int update = chapterMapper.updateByPrimaryKeySelective(chapter);

        queryModuleIdAndDelCache(chapter.getId());
        return update;
    }

    @Override
    public int incrNewCommentCount(Integer chapterId, int count) {
        int incr = chapterDao.updateIncrNewCommentCount(chapterId, count);
        queryModuleIdAndDelCache(chapterId);
        return incr;
    }

    /**
     * 只在新增section的时候调用，没有设置缓存
     * @param chapterId id
     * @return chapter
     */
    @Override
    public CmsChapter getChapterById(Integer chapterId) {
        CmsChapter chapter = chapterMapper.selectByPrimaryKey(chapterId);
        return chapter;
    }

    private short getNextSort(Integer moduleId) {
        cmsModuleDao.selectByIdForUpdate(moduleId);
        Integer maxSort = chapterDao.selectMaxSortByModuleId(moduleId);
        int nextSort = maxSort + 1;
        return (short) nextSort;
    }

    private void queryModuleIdAndDelCache(Integer chapterId) {
        CmsChapter chapter = chapterMapper.selectByPrimaryKey(chapterId);
        chapterCacheService.delChapterByModuleId(chapter.getModuleId());
    }
}
