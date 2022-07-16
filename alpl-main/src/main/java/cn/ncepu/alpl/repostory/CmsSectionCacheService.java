package cn.ncepu.alpl.repostory;

import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.domain.CmsSectionDetail;
import cn.ncepu.alpl.model.CmsSection;

import java.util.List;

/**
 * The interface Cms section cache service.
 *
 * @author xufuhang
 * @date 2022 /5/11-1:29
 */
public interface CmsSectionCacheService {

    /**
     * 查询条件作为键，查询结果作为值
     * @param sectionList 查询结果
     * @param cacheKey
     */
    void setSectionList(List<CmsSection> sectionList, CacheKey<CmsSection> cacheKey);

    void setSectionDetail(CmsSectionDetail sectionDetail);

    void delSectionDetail(Integer sectionId);

    /**
     * 条件查询的缓存结果，同一个查询条件将返回同样的结果
     *
     * @param cacheKey@return 查询结果
     */
    List<CmsSection> getSectionList(CacheKey<CmsSection> cacheKey);

    CmsSectionDetail getSectionDetail(Integer id);

    void delSectionList(Integer chapterId);
}
