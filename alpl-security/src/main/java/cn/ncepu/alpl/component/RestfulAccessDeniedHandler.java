package cn.ncepu.alpl.component;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.api.ResultEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
@author xufuhang
@date 2022/3/26-16:48
*/
/**
 * 自定义返回结果：没有权限访问时
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse response,
                       AccessDeniedException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        CommonResult<String> result = CommonResult.failed(ResultEnum.FORBIDDEN);
        if (authException != null) {
            result = CommonResult.failed(authException.getMessage());
        }

        String jsonResult = JSONUtil.toJsonStr(result);
        response.getWriter().println(jsonResult);
        response.getWriter().flush();
    }
}
