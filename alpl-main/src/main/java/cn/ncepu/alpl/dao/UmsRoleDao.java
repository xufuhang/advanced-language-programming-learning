package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/4/17-0:21
 */
public interface UmsRoleDao {

    List<UmsRole> selectListByUserId(@Param("userId") Integer userId);

    List<UmsResource> selectResourceByRoleId(@Param("roleId") Integer roleId);

    List<UmsOption> selectOptionByRoleId(@Param("roleId") Integer roleId);
}
