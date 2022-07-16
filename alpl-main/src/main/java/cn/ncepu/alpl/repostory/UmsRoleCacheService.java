package cn.ncepu.alpl.repostory;

import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.model.UmsResource;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/11-1:06
 */
public interface UmsRoleCacheService {

    List<UmsResource> getResourceList(Integer roleId);

    void setResourceList(Integer roleId, List<UmsResource> resourceList);

    void delResourceList(Integer roleId);

    List<UmsOption> getOptionListByRoleId(Integer roleId);

    void setOptionList(Integer roleId, List<UmsOption> optionList);

    void delOptionList(Integer roleId);
}
