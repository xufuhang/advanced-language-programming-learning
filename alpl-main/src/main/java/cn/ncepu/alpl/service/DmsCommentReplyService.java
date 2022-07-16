package cn.ncepu.alpl.service;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.domain.DmsCommentReplyDetail;
import cn.ncepu.alpl.model.DmsCommentReply;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

/**
 * @author xufuhang
 */
public interface DmsCommentReplyService{

    int create(DmsCommentReply commentReply, Principal principal);

    int delete(Integer commentReplyId);

    int deleteSelf(Integer commentReplyId, Principal principal);

    CommonPage<DmsCommentReplyDetail> selectPageBySectionId(Integer sectionId,
                                                            Integer pageNum,
                                                            Integer pageSize,
                                                            Boolean isOrderByLikes,
                                                            Boolean isOrderByCreateTime, Principal principal);

    CommonPage<DmsCommentReply> fetchList(int pageNum, int pageSize);

    int stars(DmsCommentReply reply, Integer count, Principal principal);

    int update(DmsCommentReply reply);

    @Transactional(rollbackFor = Exception.class)
    int topping(Integer id);
}
