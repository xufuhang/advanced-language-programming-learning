package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.domain.CmsSectionDetail;
import cn.ncepu.alpl.model.CmsSection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cn.ncepu.program.learning.domain.CmsSection
 */
public interface CmsSectionDao {

    Integer selectMaxSortByChapterId(@Param("chapterId") Integer chapterId);

    List<CmsSection> selectByCondition(@Param("section") CmsSection section,
                                       @Param("sorted") boolean sorted);

    CmsSectionDetail selectDetailById(@Param("id") Integer id);

    int updateSortGreaterThanTheNum(@Param("section") CmsSection section);

    int updateIncrCommentCount(@Param("id") Integer id, @Param("num") int num);

    int updateIncrNewCommentCount(@Param("id") Integer id);
}




