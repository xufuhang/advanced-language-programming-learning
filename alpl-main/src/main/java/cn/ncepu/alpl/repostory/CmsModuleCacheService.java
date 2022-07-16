package cn.ncepu.alpl.repostory;

import cn.ncepu.alpl.model.CmsModule;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/13-14:30
 */
public interface CmsModuleCacheService {

    void setModuleList(List<CmsModule> moduleList);

    void delModuleList();

    List<CmsModule> getModuleList();
}
