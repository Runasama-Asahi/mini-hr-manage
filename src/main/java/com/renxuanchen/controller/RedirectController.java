package com.renxuanchen.controller;

import com.renxuanchen.common.ResultObj;
import com.renxuanchen.security.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 重定向控制器
 * 处理页面跳转和认证相关请求
 */
@Controller
public class RedirectController {

    @Autowired
    private AuthService authService;

    /**
     * 首页跳转
     * @return 首页
     */
    @GetMapping("/")
    public String index(){
        return "redirect:/index";
    }

    /**
     * 页面跳转
     * @param url 页面路径
     * @return 页面名称
     */
    @GetMapping("/{url}")
    public String url(@PathVariable("url") String url){
        if(url.equals("")) url = "index";
        return url;
    }

    /**
     * 处理favicon请求
     */
    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

    /**
     * 用户登录页面
     * @return 登录页面
     */
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    
    /**
     * 用户登录API
     * @param loginname 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultObj login(String loginname, String password){
        return authService.login(loginname, password);
    }

    /**
     * 用户登出
     * @return 登录页面
     */
    @GetMapping("/logout")
    public String logout(){
        authService.logout();
        return "login";
    }
}
