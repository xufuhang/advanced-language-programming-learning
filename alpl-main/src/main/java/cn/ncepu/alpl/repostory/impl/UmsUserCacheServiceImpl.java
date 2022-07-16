package cn.ncepu.alpl.repostory.impl;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.config.RedisKeyPrefix;
import cn.ncepu.alpl.domain.UmsUserDetail;
import cn.ncepu.alpl.model.*;
import cn.ncepu.alpl.service.RedisService;
import cn.ncepu.alpl.repostory.UmsUserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
@author xufuhang
@date 2022/3/30-21:48
*/
@Service
public class UmsUserCacheServiceImpl implements UmsUserCacheService {

    @Autowired
    RedisService redisService;

    @Override
    public void setUserPage(CacheKey<String> cacheKey, CommonPage<UmsUser> page) {
        String key = genUserPageKey(cacheKey);
        redisService.set(key, page, RedisKeyPrefix.UMS_USER_PAGE.getExpire());
    }

    @Override
    public void setUserDetailByUsername(UmsUserDetail userDetail) {
        String key = genUserDetailKey(userDetail.getUsername());
        redisService.set(key, userDetail, RedisKeyPrefix.UMS_USER_DETAIL.getExpire());
    }

    @Override
    public void delAllUserPage() {
        String pattern = RedisKeyPrefix.UMS_USER_PAGE.getKeyPrefix() + "*";
        Set<String> keys = redisService.keys(pattern);
        redisService.del(keys);
    }

    @Override
    public void delUserDetailByUsername(String username) {
        String key = genUserDetailKey(username);
        redisService.del(key);
    }

    @Override
    public CommonPage<UmsUser> getUserList(CacheKey<String> cacheKey) {
        String key = genUserPageKey(cacheKey);
        return (CommonPage<UmsUser>) redisService.get(key);
    }

    @Override
    public UmsUserDetail getUserDetailByUsername(String username) {
        String key = genUserDetailKey(username);
        return (UmsUserDetail) redisService.get(key);
    }


    private String genUserDetailKey(String username) {
        return RedisKeyPrefix.UMS_USER_DETAIL.getKeyPrefix() + username;
    }

    private String genUserPageKey(CacheKey<String> cacheKey) {
        return RedisKeyPrefix.UMS_USER_PAGE.getKeyPrefix() + cacheKey.toString();
    }
}
