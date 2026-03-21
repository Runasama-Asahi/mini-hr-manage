package com.renxuanchen.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        //权限设置
        Map<String,String> map = new Hashtable<>();
        map.put("/index","authc");//是否登录
        map.put("/","authc");
        factoryBean.setFilterChainDefinitionMap(map);
        //设置登录页面
        factoryBean.setLoginUrl("/login");//未登录跳转login
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") SysUserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        return manager;
    }

    @Bean("userRealm")
    public SysUserRealm userRealm(){
        return new SysUserRealm();
    }
}
