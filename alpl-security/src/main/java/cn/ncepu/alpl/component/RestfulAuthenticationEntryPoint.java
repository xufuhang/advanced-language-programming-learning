package cn.ncepu.alpl.component;

import cn.hutool.json.JSONUtil;
import cn.ncepu.alpl.api.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
@author xufuhang
@date 2022/3/26-17:04
*/
/**
 * 自定义返回结果：未登录或登录过期
 */
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        CommonResult<String> result = CommonResult.unauthorized(e.getMessage());

        String jsonResult = JSONUtil.toJsonStr(result);
        response.getWriter().println(jsonResult);
        response.getWriter().flush();
    }
}
