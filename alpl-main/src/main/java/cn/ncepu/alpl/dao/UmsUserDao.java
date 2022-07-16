package cn.ncepu.alpl.dao;

import cn.ncepu.alpl.domain.UmsUserDetail;
import cn.ncepu.alpl.model.UmsRole;
import cn.ncepu.alpl.model.UmsUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
@author xufuhang
@date 2022/3/26-20:20
*/
public interface UmsUserDao {

    UmsUserDetail selectUserDetailByUsername(@Param("username") String username);

    UmsUser selectUserByUsername(@Param("username") String username);

    List<UmsUserDetail> selectDetailListByCondition(@Param("user") UmsUser user);

    List<UmsRole> selectRoleListByUserId(@Param("id") Integer id);

    int insertList(@Param("list") List<UmsUser> userList);

    int updateById(@Param("user") UmsUser user);

    List<UmsUser> selectByKeywordAndRoleName(@Param("keyword") String keyword,
                                             @Param("roleId") Integer roleId);
}
