package com.renxuanchen.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码编码器初始化类
 * 用于在系统启动时对现有用户的密码进行BCrypt加密
 * 
 * 注意：此工具类仅在首次升级时使用，之后可以删除或禁用
 */
@Slf4j
@Component
public class PasswordEncoderInit implements CommandLineRunner {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 检查是否需要加密密码
        // 这里可以根据实际情况调整逻辑，例如添加一个配置项来控制是否执行

        // 查询所有用户
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("password");
        queryWrapper.ne("password", "");

        userMapper.selectList(queryWrapper).forEach(user -> {
            // 检查密码是否已经是BCrypt格式（BCrypt密码以$2a$或$2b$或$2y$开头）
            String password = user.getPassword();
            if (password != null && !password.startsWith("$2")) {
                // 对密码进行BCrypt加密
                String encodedPassword = passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
                userMapper.updateById(user);
                log.info("已对用户 {} 的密码进行BCrypt加密", user.getLoginname());
            }
        });

        log.info("密码加密初始化完成");
    }
}
