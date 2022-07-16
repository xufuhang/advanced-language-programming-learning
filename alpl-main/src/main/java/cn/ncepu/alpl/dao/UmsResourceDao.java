package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/4/17-0:14
 */
public interface UmsResourceDao {

    List<UmsResource> selectListByUserId(@Param("userId") Integer userId);

    List<UmsResource> selectByRoleId(@Param("roleId") Integer roleId);

}
