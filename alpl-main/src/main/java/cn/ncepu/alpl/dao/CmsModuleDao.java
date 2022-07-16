package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.domain.CmsChapterDetail;
import cn.ncepu.alpl.model.CmsModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cn.ncepu.program.learning.domain.CmsModule
 */
public interface CmsModuleDao {

    List<CmsModule> selectAllForUpdate();

    Integer selectMaxSort();

    List<CmsModule> selectByIdForUpdate(@Param("moduleId") Integer moduleId);

    List<CmsChapterDetail> selectDetailById(@Param("moduleId") Integer moduleId);

    int updateSortGreaterThanTheNum(@Param("sort") Short sort);

}




