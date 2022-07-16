package cn.ncepu.alpl.service;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/3/27-13:15
 */
public interface UmsRoleService {
    /**
     * 添加角色
     */
    int create(UmsRole role);

    /**
     * 修改角色信息
     */
    int update(UmsRole role);

    /**
     * 批量删除角色
     * @param ids
     */
    int delete(List<Integer> ids);

    /**
     * 获取所有角色列表
     */
    List<UmsRole> list();

    /**
     * 分页获取角色列表
     * @return
     */
    CommonPage<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 获取角色相关资源
     * @param roleId
     */
    List<UmsResource> listResource(Integer roleId);

    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResource(Integer roleId, List<Integer> resourceIds);

    List<UmsResource> queryResourceByRoleIdList(List<UmsRole> roleList);

    List<UmsOption> queryOptionListByRoleIdList(List<UmsRole> roleList);
}
