package cn.ncepu.alpl.repostory.impl;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.config.RedisKeyPrefix;
import cn.ncepu.alpl.domain.DmsCommentDetail;
import cn.ncepu.alpl.repostory.DmsCommentCacheService;
import cn.ncepu.alpl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author xufuhang
 * @date 2022/5/14-17:02
 */
@Service
public class DmsCommentCacheServiceImpl implements DmsCommentCacheService {

    @Autowired
    RedisService redisService;

    @Override
    public void setCommentDetailPage(CacheKey<Integer> cacheKey, CommonPage<DmsCommentDetail> page) {
        String key = genCommentDetailPageKey(cacheKey);
        redisService.set(key, page, RedisKeyPrefix.DMS_COMMENT_DETAIL_PAGE.getExpire());
    }

    @Override
    public void setStarsCommentIdByUsername(String username, Set<Integer> commentIdSet) {
        String key = genStarsCommentIdSetKey(username);
        redisService.set(key, commentIdSet, RedisKeyPrefix.DMS_USER_COMMENT_STARS.getExpire());
    }

    @Override
    public void delCommentDetailBySectionId(Integer sectionId) {
        String pattern = genKeyPrefixBySectionId(sectionId);
        Set<String> keys = redisService.keys(pattern);
        redisService.del(keys);
    }

    @Override
    public void delUserCommentStars(String username) {
        String key = genStarsCommentIdSetKey(username);
        redisService.del(key);
    }

    @Override
    public void delAllDetailPage() {
        String pattern = RedisKeyPrefix.DMS_COMMENT_DETAIL_PAGE.getKeyPrefix() + "*";
        Set<String> keys = redisService.keys(pattern);
        redisService.del(keys);
    }

    @Override
    public CommonPage<DmsCommentDetail> getCommentDetailPage(CacheKey<Integer> cacheKey) {
        String key = genCommentDetailPageKey(cacheKey);
        return (CommonPage<DmsCommentDetail>) redisService.get(key);
    }

    @Override
    public Set<Integer> getStarsCommentIdSetByUsername(String username) {
        String key = genStarsCommentIdSetKey(username);
        return (Set<Integer>) redisService.get(key);
    }

    private String genStarsCommentIdSetKey(String username) {
        return RedisKeyPrefix.DMS_USER_COMMENT_STARS.getKeyPrefix() + username;
    }

    private String genCommentDetailPageKey(CacheKey<Integer> cacheKey) {
        return genKeyPrefixBySectionId(cacheKey.getKey()) + ":" + cacheKey;
    }

    private String genKeyPrefixBySectionId(Integer sectionId) {
        return RedisKeyPrefix.DMS_COMMENT_DETAIL_PAGE.getKeyPrefix() + sectionId + "*";
    }
}
