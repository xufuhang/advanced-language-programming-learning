package cn.ncepu.alpl.repostory;

import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.domain.CmsChapterDetail;
import cn.ncepu.alpl.model.CmsChapter;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/13-15:28
 */
public interface CmsChapterCacheService {

    void setChapterDetailList(Integer moduleId, List<CmsChapterDetail> chapterDetailList);

    void setChapterList(CacheKey<CmsChapter> cacheKey, List<CmsChapter> chapterList);

    void delChapterByModuleId(Integer chapterId);

    void delAllDetailList();

    List<CmsChapterDetail> getChapterDetailList(Integer moduleId);

    List<CmsChapter> getChapterList(CacheKey<CmsChapter> cacheKey);
}
