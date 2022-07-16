package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.model.UmsUserRoleRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/12-15:38
 */
public interface UmsUserRoleRelationDao {

    int insertList(@Param("list") List<UmsUserRoleRelation> list);
}
