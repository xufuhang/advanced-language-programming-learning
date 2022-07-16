package cn.ncepu.alpl.repostory;

import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.domain.DmsCommentReplyDetail;

import java.util.Set;

/**
 * @author xufuhang
 * @date 2022/5/13-17:32
 */
public interface DmsCommentReplyCacheService {

    /**
     * 同一个评论下的所有回复分页，都有相同前缀：固定前缀+commentId
     * @param commentId 评论id
     */
    void delCommentReplyDetailByCommentId(Integer commentId);

    CommonPage<DmsCommentReplyDetail> getCommentReplyDetailPage(CacheKey<Integer> cacheKey);

    void setCommentReplyDetailPage(CacheKey<Integer> cacheKey, CommonPage<DmsCommentReplyDetail> page);

    Set<Integer> getStarsCommentReplyIdSetByUsername(String username);

    void setStarsCommentReplyIdByUsername(String username, Set<Integer> commentIdSet);

    void delUserCommentReplyStars(String username);

    void delAllDetailPage();
}
