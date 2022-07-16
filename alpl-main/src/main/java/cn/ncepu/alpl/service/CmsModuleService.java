package cn.ncepu.alpl.service;

import cn.ncepu.alpl.model.CmsModule;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

/**
 *
 */
public interface CmsModuleService {

    @Transactional(rollbackFor = Exception.class)
    int create(CmsModule module, Principal principal);

    int delete(Integer moduleId);

    List<CmsModule> selectList();

    int updatePosition(CmsModule module);

    int updateByIdSelective(CmsModule module);
}
