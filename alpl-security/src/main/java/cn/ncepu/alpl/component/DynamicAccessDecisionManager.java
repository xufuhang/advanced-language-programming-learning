package cn.ncepu.alpl.component;

import cn.ncepu.alpl.service.DynamicResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

/**
@author xufuhang
@date 2022/3/26-22:23
动态权限决策管理器，用于判断用户是否有访问权限
*/
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    private static Map<String, String> accessDeniedMessageMap;

    @Autowired
    DynamicResourceLoader dynamicResourceLoader;

    @PostConstruct
    public void loadAccessDeniedMessageMap(){
        accessDeniedMessageMap = dynamicResourceLoader.loadAccessDeniedMessageMap();
    }

    public void clearAccessDeniedMessageMap() {
        accessDeniedMessageMap = null;
    }

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        /*
        虽然所需权限是数组，但resource表的设计是url匹配串是不重复的，
        即一个path只会被一个url匹配串匹配到，则一个path只需要一个权限，
        若后续修改为需要多个权限，修改此处即可。
        已修改：需要所有权限，path可以被多个url匹配串匹配，表示需要多个权限
         */
        if (accessDeniedMessageMap == null) {
            loadAccessDeniedMessageMap();
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute configAttribute : configAttributes) {
            String needAuthority = configAttribute.getAttribute();
            if (hasAuthority(authorities, needAuthority)) {
                return;
            }
//            if (!hasAuthority(authorities, needAuthority)) {
//                String message = accessDeniedMessageMap.get(needAuthority);
//                throw new AccessDeniedException(message);
//            }
        }
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String needAuthority) {
        for (GrantedAuthority grantedAuthority : authorities) {
            if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
