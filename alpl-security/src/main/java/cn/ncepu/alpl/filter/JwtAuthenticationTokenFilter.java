package cn.ncepu.alpl.filter;

import cn.hutool.core.util.StrUtil;
import cn.ncepu.alpl.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
@author xufuhang
@date 2022/3/26-17:21
*/
/**
 * JWT登录授权过滤器
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorization = request.getHeader(jwtUtil.getTokenHeaderKey());
        if (StrUtil.isEmpty(authorization) || !authorization.startsWith(jwtUtil.getTokenPrefix())) {
//            log.warn("token为空");
            chain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(jwtUtil.getTokenPrefix().length());
        String username = jwtUtil.getUserNameFromToken(token);
        if (StrUtil.isEmpty(username) || SecurityContextHolder.getContext().getAuthentication() != null) {
            log.info("username为空或已认证");
            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!jwtUtil.validateToken(token, userDetails)) {
            log.warn("token无效");
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }


}
