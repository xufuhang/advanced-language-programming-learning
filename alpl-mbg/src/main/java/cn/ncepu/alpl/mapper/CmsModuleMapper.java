package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.CmsModule;
import cn.ncepu.alpl.model.CmsModuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsModuleMapper {
    long countByExample(CmsModuleExample example);

    int deleteByExample(CmsModuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CmsModule record);

    int insertSelective(CmsModule record);

    List<CmsModule> selectByExample(CmsModuleExample example);

    CmsModule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CmsModule record, @Param("example") CmsModuleExample example);

    int updateByExample(@Param("record") CmsModule record, @Param("example") CmsModuleExample example);

    int updateByPrimaryKeySelective(CmsModule record);

    int updateByPrimaryKey(CmsModule record);
}