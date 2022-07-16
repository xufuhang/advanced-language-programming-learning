package cn.ncepu.alpl.domain;

import cn.ncepu.alpl.model.UmsResource;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * 继承Spring Security的用户接口，是自定义用户类和UserDetails类的桥梁，
 * 传入自定义用户对象，生成UserDetails需要的数据
@author xufuhang
@date 2022/3/25-0:56
*/
@Data
public class SecurityUser implements UserDetails {

    private Collection<GrantedAuthority> authorities;

    private String password;

    private String username;

    private Boolean isEnabled;

    public SecurityUser(UmsUserDetail userDetail) {
        this.authorities = new HashSet<>();
        for (UmsResource resource : userDetail.getResourceList()) {
            //id+':'+name作为表示资源权限，url匹配串表示资源
            String resAuthority = resource.getId() + ":" + resource.getName();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(resAuthority);
            this.authorities.add(authority);
        }
        this.password = userDetail.getPassword();
        this.username = userDetail.getUsername();
        this.isEnabled = userDetail.getStatus() == 1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
