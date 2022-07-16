package cn.ncepu.alpl.repostory.impl;

import cn.ncepu.alpl.config.RedisKeyPrefix;
import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.repostory.UmsRoleCacheService;
import cn.ncepu.alpl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/11-1:07
 */
@Service
public class UmsRoleCacheServiceImpl implements UmsRoleCacheService {

    @Autowired
    RedisService redisService;

    @Override
    public void setResourceList(Integer roleId, List<UmsResource> resourceList) {
        String key = genResourceListKey(roleId);
        redisService.set(key, resourceList, RedisKeyPrefix.UMS_RESOURCE_LIST.getExpire());
    }

    @Override
    public void setOptionList(Integer roleId, List<UmsOption> optionList) {
        String key = genOptionListKey(roleId);
        redisService.set(key, optionList, RedisKeyPrefix.UMS_OPTION_LIST.getExpire());
    }

    @Override
    public void delResourceList(Integer roleId) {
        String key = genResourceListKey(roleId);
        redisService.del(key);
    }

    @Override
    public void delOptionList(Integer roleId) {
        String key = genOptionListKey(roleId);
        redisService.del(key);
    }

    @Override
    public List<UmsResource> getResourceList(Integer roleId) {
        String key = genResourceListKey(roleId);
        return (List<UmsResource>) redisService.get(key);
    }

    @Override
    public List<UmsOption> getOptionListByRoleId(Integer roleId) {
        String key = genOptionListKey(roleId);
        return (List<UmsOption>) redisService.get(key);
    }

    private String genResourceListKey(Integer roleId) {
        return RedisKeyPrefix.UMS_RESOURCE_LIST.getKeyPrefix() + roleId;
    }

    private String genOptionListKey(Integer roleId) {
        return RedisKeyPrefix.UMS_OPTION_LIST.getKeyPrefix() + roleId;
    }
}
