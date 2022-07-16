package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.domain.DmsCommentReplyDetail;
import cn.ncepu.alpl.model.DmsCommentReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cn.ncepu.alpl.model.DmsCommentReply
 */
public interface DmsCommentReplyDao {

    List<DmsCommentReplyDetail> selectByCommentId(@Param("commentId") Integer commentId,
                                                  @Param("sortByStars") Boolean sortByStars,
                                                  @Param("sortByCreateTime") Boolean sortByCreateTime);

    int deleteByIdAndFromUserId(@Param("commentReplyId") Integer commentReplyId, @Param("fromUserId") Integer fromUserId);

    int updateIncrStars(@Param("id") Integer id, @Param("count") Integer count);

    List<DmsCommentReply> selectByCommentIdForUpdate(@Param("commentId") Integer commentId);

    int selectMaxToppingSortByCommentId(@Param("commentId") Integer commentId);
}




