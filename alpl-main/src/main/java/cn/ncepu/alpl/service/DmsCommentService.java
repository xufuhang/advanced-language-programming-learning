package cn.ncepu.alpl.service;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.domain.DmsCommentDetail;
import cn.ncepu.alpl.model.DmsComment;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

/**
@author xufuhang
@date 2022/3/29-15:26
*/
public interface DmsCommentService{

    int create(DmsComment comment, Principal principal);

    int deleteSelf(Integer commentId, Principal principal);

    int deleteById(Integer commentId);

    int update(DmsComment comment);

    int stars(DmsComment comment, Integer count, Principal principal);

    @Transactional(rollbackFor = Exception.class)
    int pinned(Integer id);

    /**
     * 该方法不缓存，用于扫描清理不使用的文件
     */
    CommonPage<DmsComment> fetchList(int pageNum, int pageSize);

    CommonPage<DmsCommentDetail> fetchDetailList(Principal principal,
                                                 Integer sectionId,
                                                 Integer pageNum,
                                                 Integer pageSize,
                                                 Boolean sortByStars,
                                                 Boolean sortByCreateTime);

    int incrCommentReplyCount(Integer commentId, int i);
}
