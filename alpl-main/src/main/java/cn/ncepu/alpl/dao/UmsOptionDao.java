package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.model.UmsOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/7-11:10
 */
public interface UmsOptionDao {

    List<UmsOption> queryByRoleId(@Param("roleId") Integer roleId);
}
