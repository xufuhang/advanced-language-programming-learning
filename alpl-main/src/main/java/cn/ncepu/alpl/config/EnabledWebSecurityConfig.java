package cn.ncepu.alpl.config;

import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.service.DynamicResourceLoader;
import cn.ncepu.alpl.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
@author xufuhang
@date 2022/3/27-0:45
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EnabledWebSecurityConfig extends WebSecurityConfig {

    private final int DEFAULT_SIZE = 32;

    /**
     * 权限定义策略接口，定义权限如何通过资源生成，即给定资源，返回与该资源对应的权限
     */
    public interface AuthorityDefinitionPolicy {
        String getAuthorityByResource(UmsResource resource);
    }

    @Bean
    AuthorityDefinitionPolicy authorityDefinitionPolicy() {
        return resource -> {
            String authority = resource.getId() + ":" + resource.getName();
            return authority;
        };
    }

    @Bean
    DynamicResourceLoader dynamicResourceLoader() {
        //不要在此处获取资源数据，此时方法是生成Bean，mapper还没有注入
        return new DynamicResourceLoader() {

            @Autowired
            UmsResourceService resourceService;
            @Autowired
            AuthorityDefinitionPolicy authorityDefinitionPolicy;

            @Override
            public Map<String, ConfigAttribute> loadResourceMap() {
                // 不能作为成员变量，因为这个是加载方法，会在运行中多次调用，数据库的数据可能会改变
                List<UmsResource> resources = resourceService.list();
                Map<String, ConfigAttribute> resourceMap = new HashMap<>(DEFAULT_SIZE);
                for (UmsResource resource : resources) {
                    resourceMap.put(resource.getUrl(),
                            new SecurityConfig(authorityDefinitionPolicy.getAuthorityByResource(resource)));
                }
                return resourceMap;
            }
            @Override
            public Map<String, String> loadAccessDeniedMessageMap() {
                List<UmsResource> resources = resourceService.list();
                Map<String, String> accessDeniedMessageMap = new HashMap<>(DEFAULT_SIZE);
                for (UmsResource resource : resources) {
                    String authority = authorityDefinitionPolicy.getAuthorityByResource(resource);
                    String key = authority;
                    String rejectMessage = resource.getRejectMessage();
                    accessDeniedMessageMap.put(key, rejectMessage);
                }
                return accessDeniedMessageMap;
            }
        };
    }
}
