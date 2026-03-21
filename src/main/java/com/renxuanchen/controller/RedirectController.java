package com.renxuanchen.controller;

import com.renxuanchen.common.ResultObj;
import com.renxuanchen.shiro.ActiverUser;
import com.renxuanchen.util.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedirectController {

    @GetMapping("/{url}")
    public String url(@PathVariable("url") String url){
        if(url.equals("")) url = "index";
        return url;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultObj login(String loginname, String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginname,password);
        try {
            subject.login(token);
            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();//封装的用户信息
            WebUtils.getSession().setAttribute("user", activerUser.getUser());//抓取user用户给到session
            return ResultObj.LOGIN_SUCCESS;
        } catch (UnknownAccountException e) {
            return ResultObj.LOGIN_ERROR_LOGINNAME;
        } catch (IncorrectCredentialsException e) {
            return ResultObj.LOGIN_ERROR_PASS;
        }
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

}
