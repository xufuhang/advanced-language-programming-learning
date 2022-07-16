package cn.ncepu.alpl.config;

import cn.ncepu.alpl.component.DynamicAccessDecisionManager;
import cn.ncepu.alpl.component.DynamicSecurityMetadataSource;
import cn.ncepu.alpl.component.RestfulAccessDeniedHandler;
import cn.ncepu.alpl.component.RestfulAuthenticationEntryPoint;
import cn.ncepu.alpl.filter.DynamicSecurityFilter;
import cn.ncepu.alpl.filter.JwtAuthenticationTokenFilter;
import cn.ncepu.alpl.service.DynamicResourceLoader;
import cn.ncepu.alpl.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
@author xufuhang
@date 2022/3/25-11:03
*/
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    DynamicResourceLoader dynamicResourceLoader;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        此处是总配置，添加了自定义JWT过滤器，自行认证，添加了Security过滤器，自行鉴权。是叠加处理的
        所以此处还要配置白名单等
         */
        for (String url : ignoreUrlConfig().getIgnoreUrls()) {
            http.authorizeRequests().antMatchers(url).permitAll();
        }
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll();
        // 任何请求需要身份认证
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                // 自定义未登录处理器
                .accessDeniedHandler(restfulAccessDeniedHandler())
                // 自定义未授权处理器
                .authenticationEntryPoint(restfulAuthenticationEntryPoint())
                // 自定义JWT过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 自定义权限拦截器
        if (dynamicResourceLoader != null) {
            http.authorizeRequests().and().
                    addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IgnoreUrlConfig ignoreUrlConfig() {
        return new IgnoreUrlConfig();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @ConditionalOnBean(name = "dynamicResourceLoader")
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    @ConditionalOnBean(name = "dynamicResourceLoader")
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @ConditionalOnBean(name = "dynamicResourceLoader")
    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }

    /**
     *  I do not understand what is this
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint() {
        return new RestfulAuthenticationEntryPoint();
    }



}
