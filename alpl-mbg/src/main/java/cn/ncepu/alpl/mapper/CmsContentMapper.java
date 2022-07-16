package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.CmsContent;
import cn.ncepu.alpl.model.CmsContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsContentMapper {
    long countByExample(CmsContentExample example);

    int deleteByExample(CmsContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CmsContent record);

    int insertSelective(CmsContent record);

    List<CmsContent> selectByExample(CmsContentExample example);

    CmsContent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CmsContent record, @Param("example") CmsContentExample example);

    int updateByExample(@Param("record") CmsContent record, @Param("example") CmsContentExample example);

    int updateByPrimaryKeySelective(CmsContent record);

    int updateByPrimaryKey(CmsContent record);
}