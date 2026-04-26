package com.renxuanchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renxuanchen.common.UserTreeNode;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.SysUserMapper;
import com.renxuanchen.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    public List<UserTreeNode> getUserTree() {
        List<SysUser> users = this.list();
        return users.stream()
                .filter(user -> user.getAvailable() == 1) // 只显示可用用户
                .map(user -> new UserTreeNode(
                        user.getId(),
                        user.getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public SysUser getByEmail(String email) {
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, email));
    }
}
