package cn.ncepu.alpl.repostory;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.domain.DmsCommentDetail;

import java.util.Set;

/**
 * @author xufuhang
 * @date 2022/5/13-17:15
 */
public interface DmsCommentCacheService {

    /**
     * 不同sectionId下的评论互不影响，只删除同一section下的评论缓存，则添加sectionId前缀
     * @param cacheKey 查询参数
     * @param page 缓存数据
     */
    void setCommentDetailPage(CacheKey<Integer> cacheKey, CommonPage<DmsCommentDetail> page);

    void delCommentDetailBySectionId(Integer sectionId);

    CommonPage<DmsCommentDetail> getCommentDetailPage(CacheKey<Integer> cacheKey);

    Set<Integer> getStarsCommentIdSetByUsername(String username);

    void setStarsCommentIdByUsername(String username, Set<Integer> commentIdSet);

    void delUserCommentStars(String username);

    void delAllDetailPage();
}
