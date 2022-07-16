package cn.ncepu.alpl.service;

import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.model.UmsRole;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/4/15-21:56
 */
public interface UmsResourceService {

    List<UmsResource> list();

    List<UmsResource> queryByRoleList(List<UmsRole> roleList);
}
