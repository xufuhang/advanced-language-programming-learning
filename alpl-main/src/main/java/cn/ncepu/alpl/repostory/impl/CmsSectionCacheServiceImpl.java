package cn.ncepu.alpl.repostory.impl;

import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.config.RedisKeyPrefix;
import cn.ncepu.alpl.domain.CmsSectionDetail;
import cn.ncepu.alpl.model.CmsSection;
import cn.ncepu.alpl.repostory.CmsSectionCacheService;
import cn.ncepu.alpl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author xufuhang
 * @date 2022/5/11-1:29
 */
@Service
public class CmsSectionCacheServiceImpl implements CmsSectionCacheService {

    @Autowired
    RedisService redisService;

    @Override
    public void setSectionList(List<CmsSection> sectionList, CacheKey<CmsSection> cacheKey) {
        String key = RedisKeyPrefix.CMS_SECTION_LIST.getKeyPrefix() + cacheKey.getKey().toString();
        redisService.set(key, sectionList, RedisKeyPrefix.CMS_SECTION_LIST.getExpire());
    }

    public void setSectionDetail(CmsSectionDetail sectionDetail) {
        String key = RedisKeyPrefix.CMS_SECTION_DETAIL.getKeyPrefix() + sectionDetail.getId();
        redisService.set(key, sectionDetail, RedisKeyPrefix.CMS_SECTION_DETAIL.getExpire());
    }

    @Override
    public void delSectionList(Integer chapterId) {
        Set<String> keys = redisService.keys(RedisKeyPrefix.CMS_SECTION_LIST.getKeyPrefix() + "*");
        redisService.del(keys);
    }

    @Override
    public void delSectionDetail(Integer sectionId) {
        Set<String> keys = redisService.keys(RedisKeyPrefix.CMS_SECTION_LIST.getKeyPrefix() + "*");
        keys.add(RedisKeyPrefix.CMS_SECTION_DETAIL.getKeyPrefix() + sectionId);
        redisService.del(keys);
    }

    @Override
    public List<CmsSection> getSectionList(CacheKey<CmsSection> cacheKey) {
        String key = RedisKeyPrefix.CMS_SECTION_LIST.getKeyPrefix() + cacheKey.getKey().toString();
        return (List<CmsSection>) redisService.get(key);
    }

    @Override
    public CmsSectionDetail getSectionDetail(Integer sectionId) {
        String key = RedisKeyPrefix.CMS_SECTION_DETAIL.getKeyPrefix() + sectionId;
        return (CmsSectionDetail) redisService.get(key);
    }
}
