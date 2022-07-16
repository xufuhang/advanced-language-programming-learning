package cn.ncepu.alpl.repostory;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.domain.UmsUserDetail;
import cn.ncepu.alpl.model.UmsUser;

import java.util.Set;

/**
@author xufuhang
@date 2022/3/30-21:44
*/
public interface UmsUserCacheService {

    void setUserDetailByUsername(UmsUserDetail userDetail);

    void setUserPage(CacheKey<String> cacheKey, CommonPage<UmsUser> page);

    void delUserDetailByUsername(String username);

    void delAllUserPage();

    UmsUserDetail getUserDetailByUsername(String username);

    CommonPage<UmsUser> getUserList(CacheKey<String> cacheKey);
}
