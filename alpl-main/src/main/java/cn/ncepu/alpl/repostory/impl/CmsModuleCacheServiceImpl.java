package cn.ncepu.alpl.repostory.impl;

import cn.ncepu.alpl.config.RedisKeyPrefix;
import cn.ncepu.alpl.model.CmsModule;
import cn.ncepu.alpl.repostory.CmsModuleCacheService;
import cn.ncepu.alpl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/14-17:02
 */
@Service
public class CmsModuleCacheServiceImpl implements CmsModuleCacheService {

    @Autowired
    RedisService redisService;

    @Override
    public void setModuleList(List<CmsModule> moduleList) {
        String key = genModuleListKey();
        redisService.set(key, moduleList, RedisKeyPrefix.CMS_MODULE_LIST.getExpire());
    }

    @Override
    public void delModuleList() {
        String key = genModuleListKey();
        redisService.del(key);
    }

    @Override
    public List<CmsModule> getModuleList() {
        String key = genModuleListKey();
        return (List<CmsModule>) redisService.get(key);
    }

    private String genModuleListKey() {
        return RedisKeyPrefix.CMS_MODULE_LIST.getKeyPrefix();
    }
}
