package cn.ncepu.alpl.service;

import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.model.UmsRole;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/5-21:31
 */
public interface UmsOptionService {

    List<UmsOption> listAll();

    List<UmsOption> queryByRoleList(List<UmsRole> roleList);
}
