package cn.ncepu.alpl.component;

import cn.hutool.core.util.URLUtil;
import cn.ncepu.alpl.service.DynamicResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
@author xufuhang
@date 2022/3/26-21:18
*/
/**
 * 动态权限数据源，用于获取动态权限规则
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, ConfigAttribute> configAttrMap;

    @Autowired
    DynamicResourceLoader dynamicResourceLoader;

    @PostConstruct
    public void loadResourceMap(){
        configAttrMap = dynamicResourceLoader.loadResourceMap();
    }

    public void clearResourceMap(){
        configAttrMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (configAttrMap == null) {
            loadResourceMap();
        }
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        String path = URLUtil.getPath(url);

        PathMatcher pathMatcher = new AntPathMatcher();
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        for (String pattern : configAttrMap.keySet()) {
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(configAttrMap.get(pattern));
            }
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
