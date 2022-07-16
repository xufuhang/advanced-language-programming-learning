package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.model.UmsOptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsOptionMapper {
    long countByExample(UmsOptionExample example);

    int deleteByExample(UmsOptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UmsOption record);

    int insertSelective(UmsOption record);

    List<UmsOption> selectByExample(UmsOptionExample example);

    UmsOption selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UmsOption record, @Param("example") UmsOptionExample example);

    int updateByExample(@Param("record") UmsOption record, @Param("example") UmsOptionExample example);

    int updateByPrimaryKeySelective(UmsOption record);

    int updateByPrimaryKey(UmsOption record);
}