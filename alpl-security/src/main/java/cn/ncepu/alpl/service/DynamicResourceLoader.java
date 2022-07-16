package cn.ncepu.alpl.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/*
@author xufuhang
@date 2022/3/26-16:40
*/
public interface DynamicResourceLoader {

    /**
     *
     * @return 返回一个哈希表，key为url，value为唯一标识，表示匹配该url的请求需要某资源
     */
    Map<String, ConfigAttribute> loadResourceMap();

    /**
     *
     * @return 返回一个哈希表，key为唯一标识，value为message，某个资源被拒绝访问时返回的提示信息
     */
    Map<String, String> loadAccessDeniedMessageMap();

}
