package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.CmsSection;
import cn.ncepu.alpl.model.CmsSectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsSectionMapper {
    long countByExample(CmsSectionExample example);

    int deleteByExample(CmsSectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CmsSection record);

    int insertSelective(CmsSection record);

    List<CmsSection> selectByExample(CmsSectionExample example);

    CmsSection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CmsSection record, @Param("example") CmsSectionExample example);

    int updateByExample(@Param("record") CmsSection record, @Param("example") CmsSectionExample example);

    int updateByPrimaryKeySelective(CmsSection record);

    int updateByPrimaryKey(CmsSection record);
}