package com.renxuanchen.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renxuanchen.common.ResultObj;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 访问拒绝处理器
 * 处理授权失败的情况
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, 
                       HttpServletResponse response, 
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置响应内容类型为JSON
        response.setContentType("application/json;charset=UTF-8");
        // 设置响应状态码为403禁止访问
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // 创建错误响应
        ResultObj resultObj = new ResultObj(403, "权限不足：" + accessDeniedException.getMessage());

        // 将结果对象转换为JSON并写入响应
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), resultObj);
    }
}
