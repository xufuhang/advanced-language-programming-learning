package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.domain.DmsCommentDetail;
import cn.ncepu.alpl.model.CmsSection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/3/30-12:00
 */
public interface DmsCommentDao {

    List<DmsCommentDetail> selectDetailBySectionId(@Param("sectionId") Integer sectionId,
                                                   @Param("isOrderByLikes") Boolean isOrderByLikes,
                                                   @Param("isOrderByCreateTime") Boolean isOrderByCreateTime);

    int deleteByIdAndFromUserId(@Param("commentId") Integer commentId, @Param("userId") Integer id);

    List<CmsSection> selectBySectionIdForUpdate(@Param("sectionId") Integer sectionId);

    int selectMaxToppingSortBySectionId(@Param("sectionId") Integer sectionId);

    int updateIncrCommentReplyCount(@Param("commentId") Integer commentId, @Param("count") int count);

    int updateIncrStars(@Param("id") Integer id, @Param("count") int count);
}
