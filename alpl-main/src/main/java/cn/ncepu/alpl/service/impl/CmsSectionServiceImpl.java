package cn.ncepu.alpl.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.domain.CmsSectionDetail;
import cn.ncepu.alpl.mapper.CmsContentMapper;
import cn.ncepu.alpl.mapper.CmsSectionMapper;
import cn.ncepu.alpl.model.CmsChapter;
import cn.ncepu.alpl.model.CmsSection;
import cn.ncepu.alpl.model.UmsUser;
import cn.ncepu.alpl.repostory.CmsChapterCacheService;
import cn.ncepu.alpl.repostory.CmsSectionCacheService;
import cn.ncepu.alpl.service.CmsSectionService;
import cn.ncepu.alpl.dao.CmsSectionDao;
import cn.ncepu.alpl.dao.CmsChapterDao;
import cn.ncepu.alpl.model.CmsContent;
import cn.ncepu.alpl.service.CmsChapterService;
import cn.ncepu.alpl.service.UmsUserService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * The type Cms section service.
 */
@Service
@Slf4j
public class CmsSectionServiceImpl implements CmsSectionService {

    @Autowired
    private CmsChapterService chapterService;
    @Autowired
    private CmsSectionMapper sectionMapper;
    @Autowired
    private CmsContentMapper contentMapper;
    @Autowired
    private CmsSectionDao sectionDao;
    @Autowired
    private CmsChapterDao chapterDao;
    @Autowired
    private UmsUserService userService;
    @Autowired
    private CmsSectionCacheService sectionCacheService;
    @Autowired
    private CmsChapterCacheService chapterCacheService;

    @Override
    public int create(CmsSectionDetail sectionDetail, Principal principal) {
        String username = principal.getName();
        UmsUser user = userService.getUserByUsername(username);
        sectionDetail.setCreateUserId(user.getId());
        short nextSort = getNextSort(sectionDetail.getChapterId());
        sectionDetail.setSort(nextSort);

        // insert执行后会把自增id传给section
        // 此处插入section，不包括content
        int insertSection = sectionMapper.insertSelective(sectionDetail);

        // 插入内容到content表
        CmsContent content = new CmsContent();
        content.setId(sectionDetail.getId());
        content.setContent(sectionDetail.getContent());
        int insertContent = chapterDao.insertSelectiveWithId(content);

        // 删除相关缓存
        queryModuleIdAndDelCacheBySectionId(sectionDetail.getChapterId());
        sectionCacheService.delSectionList(sectionDetail.getChapterId());
        return insertSection + insertContent;
    }

    @Override
    public int delete(Integer sectionId) {
        CmsSection section = queryDetailById(sectionId);
        int delete = sectionMapper.deleteByPrimaryKey(sectionId);

        CmsChapter chapter = chapterService.getChapterById(section.getChapterId());
        chapterCacheService.delChapterByModuleId(chapter.getModuleId());
        return delete;
    }

    @Override
    public int clearNewCommentCount(Integer sectionId) {
        CmsSectionDetail sectionDetail = doQuerySectionDetail(sectionId);

        if (sectionDetail.getNewCommentCount() == 0) {
            // 优化：如果没有最新评论，无需清除最新评论消息提示
            return 1;
        }

        // 章节最新评论数量更新为0
        Integer chapterId = sectionDetail.getChapterId();
        int count = -sectionDetail.getNewCommentCount();
        int updateChapterNewCommentCount = chapterService.incrNewCommentCount(chapterId, count);

        // 小节最新评论数量更新为0
        /*
        若更新记录，则必须删除有关的所有缓存，因为不太可能去更新缓存。
        如果不作优化，每次查询都会更新记录，然后删除缓存，则缓存失去意义。
         */
        CmsSection section = new CmsSection();
        section.setId(sectionId);
        section.setNewCommentCount((short) 0);
        int updateSectionNewCommentCount = sectionMapper.updateByPrimaryKeySelective(section);

        queryModuleIdAndDelCacheBySectionId(sectionId);
        return 1;
    }

    @Override
    public CmsSectionDetail queryDetailById(Integer id) {
        CmsSectionDetail sectionDetail = doQuerySectionDetail(id);
        return sectionDetail;
    }

    @Override
    public List<CmsSection> queryByCondition(CmsSection section) {
        CacheKey<CmsSection> cacheKey = new CacheKey<>();
        cacheKey.setKey(section);
        List<CmsSection> sectionList;
        sectionList = sectionCacheService.getSectionList(cacheKey);
        if (!CollectionUtil.isEmpty(sectionList)) {
            return sectionList;
        }

        boolean sorted = true;
        sectionList = sectionDao.selectByCondition(section, sorted);

        sectionCacheService.setSectionList(sectionList, cacheKey);
        return sectionList;
    }

    @Override
    public int update(CmsSectionDetail sectionResult) {
        int update = sectionMapper.updateByPrimaryKeySelective(sectionResult);
        if (update < 1) {
            return 0;
        }

        CmsContent content = new CmsContent();
        content.setId(sectionResult.getId());
        content.setContent(sectionResult.getContent());
        int updateContent = contentMapper.updateByPrimaryKeySelective(content);
        if (updateContent < 1) {
            return 0;
        }

        queryModuleIdAndDelCacheBySectionId(sectionResult.getId());
        sectionCacheService.delSectionDetail(sectionResult.getId());
        return update + updateContent;
    }

    /**
     * 查询内容中存在的文件，用于清理不用的文件，不缓存此方法
     * @param fileClearPageNum 页码
     * @param fileClearPageSize 页面大小
     * @return 分页数据
     */
    @Override
    public CommonPage<CmsContent> queryContentPage(int fileClearPageNum, int fileClearPageSize) {
        PageHelper.startPage(fileClearPageNum, fileClearPageSize);
        List<CmsContent> contentList = contentMapper.selectByExample(null);
        CommonPage<CmsContent> page = CommonPage.genCommonPage(contentList);
        return page;
    }

    @Override
    public int incrCommentCount(Integer sectionId, int count) {
        int incr = sectionDao.updateIncrCommentCount(sectionId, count);

        queryModuleIdAndDelCacheBySectionId(sectionId);
        return incr;
    }

    @Override
    public int incrNewCommentCount(Integer sectionId) {
        int incr = sectionDao.updateIncrNewCommentCount(sectionId);
        CmsSection section = sectionCacheService.getSectionDetail(sectionId);
        chapterService.incrNewCommentCount(section.getChapterId(), 1);

        queryModuleIdAndDelCacheBySectionId(sectionId);
        sectionCacheService.delSectionDetail(sectionId);
        return incr;
    }

    @Override
    public int updatePosition(CmsSection section) {
        if (section.getSort() == null) {
            // 没有目标位置，则插入到章节末尾
            short nextSort = getNextSort(section.getChapterId());
            section.setSort(nextSort);
        } else {
            // 给小节腾出目标位置
            sectionDao.updateSortGreaterThanTheNum(section);
        }

        int update = sectionMapper.updateByPrimaryKeySelective(section);

        // 删除相关缓存
        queryModuleIdAndDelCacheBySectionId(section.getId());
        sectionCacheService.delSectionDetail(section.getId());
        return update;
    }

    private short getNextSort(Integer chapterId) {
        // 锁定小节所属章节记录
        chapterDao.selectByIdForUpdate(chapterId);
        Integer maxSort = sectionDao.selectMaxSortByChapterId(chapterId);
        //若sort为sectionCount，则sort从0开始，否则从1开始
        int nextSort = maxSort + 1;
        return (short) nextSort;
    }

    private void queryModuleIdAndDelCacheBySectionId(Integer sectionId) {
        CmsSection section = queryDetailById(sectionId);
        CmsChapter chapter = chapterService.getChapterById(section.getChapterId());
        chapterCacheService.delChapterByModuleId(chapter.getModuleId());
    }

    private CmsSectionDetail doQuerySectionDetail(Integer sectionId) {
        CmsSectionDetail sectionDetail;
        sectionDetail = sectionCacheService.getSectionDetail(sectionId);
        if (sectionDetail == null) {
            sectionDetail = sectionDao.selectDetailById(sectionId);
            sectionCacheService.setSectionDetail(sectionDetail);
        }
        return sectionDetail;
    }
}




