package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.UmsRoleOptionRelation;
import cn.ncepu.alpl.model.UmsRoleOptionRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsRoleOptionRelationMapper {
    long countByExample(UmsRoleOptionRelationExample example);

    int deleteByExample(UmsRoleOptionRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UmsRoleOptionRelation record);

    int insertSelective(UmsRoleOptionRelation record);

    List<UmsRoleOptionRelation> selectByExample(UmsRoleOptionRelationExample example);

    UmsRoleOptionRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UmsRoleOptionRelation record, @Param("example") UmsRoleOptionRelationExample example);

    int updateByExample(@Param("record") UmsRoleOptionRelation record, @Param("example") UmsRoleOptionRelationExample example);

    int updateByPrimaryKeySelective(UmsRoleOptionRelation record);

    int updateByPrimaryKey(UmsRoleOptionRelation record);
}