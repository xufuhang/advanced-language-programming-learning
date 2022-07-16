package cn.ncepu.alpl.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.ncepu.alpl.dao.CmsModuleDao;
import cn.ncepu.alpl.mapper.CmsModuleMapper;
import cn.ncepu.alpl.model.CmsModule;
import cn.ncepu.alpl.model.CmsModuleExample;
import cn.ncepu.alpl.model.UmsUser;
import cn.ncepu.alpl.repostory.CmsModuleCacheService;
import cn.ncepu.alpl.service.CmsModuleService;
import cn.ncepu.alpl.service.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.security.Principal;
import java.util.List;

/**
 *
 */
@Service
public class CmsModuleServiceImpl implements CmsModuleService {

    @Autowired
    private CmsModuleDao moduleDao;
    @Autowired
    private CmsModuleMapper moduleMapper;
    @Autowired
    private UmsUserService userService;
    @Autowired
    private CmsModuleCacheService moduleCacheService;

    @Override
    public int create(CmsModule module, Principal principal) {
        String username = principal.getName();
        UmsUser user = userService.getUserByUsername(username);
        Integer createUserId = user.getId();
        int insert = 0;
        try {
            moduleDao.selectAllForUpdate();
            Integer maxSort = moduleDao.selectMaxSort();
            int nextSort = maxSort + 1;

            module.setSort((short) nextSort);
            module.setCreateUserId(createUserId);
            insert = moduleMapper.insertSelective(module);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        moduleCacheService.delModuleList();
        return insert;
    }

    @Override
    public int delete(Integer moduleId) {
        int delete = moduleMapper.deleteByPrimaryKey(moduleId);

        moduleCacheService.delModuleList();
        return delete;
    }

    @Override
    public List<CmsModule> selectList() {
        List<CmsModule> moduleList;
        moduleList = moduleCacheService.getModuleList();
        if (!CollectionUtil.isEmpty(moduleList)) {
            return moduleList;
        }

        CmsModuleExample example = new CmsModuleExample();
        example.setOrderByClause("sort");

        moduleList = moduleMapper.selectByExample(example);
        moduleCacheService.setModuleList(moduleList);
        return moduleList;
    }

    @Override
    public int updatePosition(CmsModule module) {
        Short sort = module.getSort();
        int updateSortGreaterThan = moduleDao.updateSortGreaterThanTheNum(sort);
        int updateSort = moduleMapper.updateByPrimaryKeySelective(module);

        moduleCacheService.delModuleList();
        return updateSortGreaterThan + updateSort;
    }

    @Override
    public int updateByIdSelective(CmsModule module) {
        int update = moduleMapper.updateByPrimaryKeySelective(module);

        moduleCacheService.delModuleList();
        return update;
    }
}




