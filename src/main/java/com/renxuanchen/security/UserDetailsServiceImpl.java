package com.renxuanchen.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renxuanchen.entity.SysPermission;
import com.renxuanchen.entity.SysRole;
import com.renxuanchen.entity.SysRolePermission;
import com.renxuanchen.entity.SysRoleUser;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.SysPermissionMapper;
import com.renxuanchen.mapper.SysRoleMapper;
import com.renxuanchen.mapper.SysRolePermissionMapper;
import com.renxuanchen.mapper.SysRoleUserMapper;
import com.renxuanchen.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDetailsService实现类
 * 负责从数据库加载用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        QueryWrapper<SysUser> userQuery = new QueryWrapper<>();
        userQuery.eq("loginname", username);
        SysUser user = userMapper.selectOne(userQuery);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 查询用户角色
        QueryWrapper<SysRoleUser> roleUserQuery = new QueryWrapper<>();
        roleUserQuery.eq("uid", user.getId());
        List<SysRoleUser> roleUsers = roleUserMapper.selectList(roleUserQuery);

        List<String> roles = roleUsers.stream()
                .map(roleUser -> {
                    SysRole role = roleMapper.selectById(roleUser.getRid());
                    return role != null ? role.getName() : null;
                })
                .filter(roleName -> roleName != null)
                .collect(Collectors.toList());

        // 查询用户权限
        List<String> permissions = roleUsers.stream()
                .flatMap(roleUser -> {
                    // 查询角色对应的权限
                    QueryWrapper<SysRolePermission> rolePermissionQuery = new QueryWrapper<>();
                    rolePermissionQuery.eq("rid", roleUser.getRid());
                    List<SysRolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQuery);

                    // 查询权限详情
                    return rolePermissions.stream()
                            .map(rolePermission -> {
                                SysPermission permission = permissionMapper.selectById(rolePermission.getPid());
                                return permission != null ? permission.getTitle() : null;
                            })
                            .filter(permName -> permName != null);
                })
                .distinct()
                .collect(Collectors.toList());

        // 创建并返回CustomUserDetails
        return new CustomUserDetails(user, roles, permissions);
    }
}
