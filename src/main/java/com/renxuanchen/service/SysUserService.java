package com.renxuanchen.service;

import com.renxuanchen.common.UserTreeNode;
import com.renxuanchen.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户实体
     */
    SysUser getByEmail(String email);


    List<UserTreeNode> getUserTree();
}
