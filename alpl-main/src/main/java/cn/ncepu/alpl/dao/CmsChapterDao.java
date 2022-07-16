package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.model.CmsChapter;
import cn.ncepu.alpl.model.CmsContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
@author xufuhang
@date 2022/3/13-21:41
*/
public interface CmsChapterDao {

    int insertSelectiveWithId(@Param("content") CmsContent content);

    Integer selectMaxSortByModuleId(@Param("moduleId") Integer moduleId);

    void selectByIdForUpdate(@Param("id") Integer id);

    List<CmsChapter> selectByCondition(@Param("chapter") CmsChapter chapter);

    int updateSortGreaterThanTheNum(@Param("chapter") CmsChapter chapter);

    int updateIncrNewCommentCount(@Param("id") Integer id, @Param("count") int count);
}




