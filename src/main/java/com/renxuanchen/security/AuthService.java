package com.renxuanchen.security;

import com.renxuanchen.common.ResultObj;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

/**
 * 认证服务类
 * 处理登录、登出等认证相关逻辑
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 用户登录
     * @param loginname 用户名
     * @param password 密码
     * @return 登录结果
     */
    public ResultObj login(String loginname, String password) {
        try {
            // 使用Spring Security进行认证
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginname, password)
            );

            // 设置认证信息到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 获取用户详情
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // 将用户信息存入session
            HttpSession session = WebUtils.getSession();
            session.setAttribute("user", userDetails.getUser());
            // 确保认证信息也保存到session中
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // 返回成功结果
            return ResultObj.LOGIN_SUCCESS;

        } catch (Exception e) {
            // 认证失败
            if (e.getMessage().contains("Bad credentials")) {
                return ResultObj.LOGIN_ERROR_PASS;
            } else if (e.getMessage().contains("UserDetailsService returned null")) {
                return ResultObj.LOGIN_ERROR_LOGINNAME;
            }
            return new ResultObj(500, "登录失败：" + e.getMessage());
        }
    }

    /**
     * 用户登出
     */
    public void logout() {
        // 清除安全上下文
        SecurityContextHolder.clearContext();

        // 清除session
        HttpSession session = WebUtils.getSession();
        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
        }
    }

    /**
     * 获取当前登录用户
     * @return 当前用户
     */
    public SysUser getCurrentUser() {
        HttpSession session = WebUtils.getSession();
        return (SysUser) session.getAttribute("user");
    }

}
