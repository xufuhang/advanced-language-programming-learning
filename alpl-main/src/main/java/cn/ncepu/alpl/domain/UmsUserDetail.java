package cn.ncepu.alpl.domain;

import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.model.UmsRole;
import cn.ncepu.alpl.model.UmsUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
@author xufuhang
@date 2022/3/25-9:42
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class UmsUserDetail extends UmsUser {

    private List<UmsResource> resourceList;
    private List<UmsRole> roleList;
    private List<UmsOption> optionList;

}
