package cn.ncepu.alpl.repostory.impl;

import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.config.RedisKeyPrefix;
import cn.ncepu.alpl.domain.CmsChapterDetail;
import cn.ncepu.alpl.model.CmsChapter;
import cn.ncepu.alpl.repostory.CmsChapterCacheService;
import cn.ncepu.alpl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xufuhang
 * @date 2022/5/14-17:01
 */
@Service
public class CmsChapterCacheServiceImpl implements CmsChapterCacheService {

    @Autowired
    private RedisService redisService;

    @Override
    public void setChapterDetailList(Integer moduleId, List<CmsChapterDetail> chapterDetailList) {
        String key = genChapterDetailListKey(moduleId);
        redisService.set(key, chapterDetailList, RedisKeyPrefix.CMS_CHAPTER_DETAIL_LIST.getExpire());
    }

    @Override
    public void setChapterList(CacheKey<CmsChapter> cacheKey, List<CmsChapter> chapterList) {
        String key = genChapterListKey(cacheKey);
        redisService.set(key, chapterList, RedisKeyPrefix.CMS_CHAPTER_LIST.getExpire());
    }

    @Override
    public void delChapterByModuleId(Integer moduleId) {
        String chapterDetailListKey = genChapterDetailListKey(moduleId);
        String pattern = RedisKeyPrefix.CMS_CHAPTER_LIST.getKeyPrefix() + "*";
        Set<String> ketSet = redisService.keys(pattern);
        ketSet.add(chapterDetailListKey);
        redisService.del(ketSet);
    }

    @Override
    public void delAllDetailList() {
        String pattern = RedisKeyPrefix.CMS_CHAPTER_DETAIL_LIST.getKeyPrefix() + '*';
        Set<String> keys = redisService.keys(pattern);
        redisService.del(keys);
    }

    @Override
    public List<CmsChapterDetail> getChapterDetailList(Integer moduleId) {
        String key = genChapterDetailListKey(moduleId);
        return (List<CmsChapterDetail>) redisService.get(key);
    }

    @Override
    public List<CmsChapter> getChapterList(CacheKey<CmsChapter> cacheKey) {
        String key = genChapterListKey(cacheKey);
        return (List<CmsChapter>) redisService.get(key);
    }

    private String genChapterDetailListKey(Integer moduleId) {
        return RedisKeyPrefix.CMS_CHAPTER_DETAIL_LIST.getKeyPrefix() + moduleId;
    }

    private String genChapterListKey(CacheKey<CmsChapter> cacheKey) {
        return RedisKeyPrefix.CMS_CHAPTER_LIST.getKeyPrefix() + cacheKey.getKey().toString();
    }
}
