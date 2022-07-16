package cn.ncepu.alpl.repostory.impl;

import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.config.RedisKeyPrefix;
import cn.ncepu.alpl.domain.DmsCommentReplyDetail;
import cn.ncepu.alpl.repostory.DmsCommentReplyCacheService;
import cn.ncepu.alpl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author xufuhang
 * @date 2022/5/14-17:02
 */
@Service
public class DmsCommentReplyCacheServiceImpl implements DmsCommentReplyCacheService {

    @Autowired
    RedisService redisService;

    @Override
    public void setCommentReplyDetailPage(CacheKey<Integer> cacheKey, CommonPage<DmsCommentReplyDetail> page) {
        String key = genCommentReplyDetailPageKey(cacheKey);
        redisService.set(key, page, RedisKeyPrefix.DMS_COMMENT_DETAIL_PAGE.getExpire());
    }

    @Override
    public void setStarsCommentReplyIdByUsername(String username, Set<Integer> commentReplyIdSet) {
        String key = genStarsCommentReplyIdSetKey(username);
        redisService.set(key, commentReplyIdSet, RedisKeyPrefix.DMS_USER_COMMENT_STARS.getExpire());
    }

    @Override
    public void delUserCommentReplyStars(String username) {
        String key = genStarsCommentReplyIdSetKey(username);
        redisService.del(key);
    }

    @Override
    public void delAllDetailPage() {
        String pattern = RedisKeyPrefix.DMS_COMMENT_REPLY_DETAIL_PAGE.getKeyPrefix() + "*";
        Set<String> keys = redisService.keys(pattern);
        redisService.del(keys);
    }

    @Override
    public void delCommentReplyDetailByCommentId(Integer commentId) {
        String pattern = genKeyPrefixByCommentId(commentId);
        Set<String> keys = redisService.keys(pattern);
        redisService.del(keys);
    }

    @Override
    public CommonPage<DmsCommentReplyDetail> getCommentReplyDetailPage(CacheKey<Integer> cacheKey) {
        String key = genCommentReplyDetailPageKey(cacheKey);
        return (CommonPage<DmsCommentReplyDetail>) redisService.get(key);
    }

    @Override
    public Set<Integer> getStarsCommentReplyIdSetByUsername(String username) {
        String key = genStarsCommentReplyIdSetKey(username);
        return (Set<Integer>) redisService.get(key);
    }

    private String genStarsCommentReplyIdSetKey(String username) {
        return RedisKeyPrefix.DMS_USER_COMMENT_REPLY_STARS.getKeyPrefix() + username;
    }

    private String genCommentReplyDetailPageKey(CacheKey<Integer> cacheKey) {
        return genKeyPrefixByCommentId(cacheKey.getKey()) + ":" + cacheKey;
    }

    private String genKeyPrefixByCommentId(Integer commentId) {
        return RedisKeyPrefix.DMS_COMMENT_REPLY_DETAIL_PAGE.getKeyPrefix() + commentId + "*";
    }
}
