package com.renxuanchen.controller;


import com.renxuanchen.common.DataGridView;
import com.renxuanchen.common.TreeNode;
import com.renxuanchen.entity.SysPermission;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.SysPermissionMapper;
import com.renxuanchen.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @RequestMapping("/loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(){
        //结合当前登录用户的角色来加载对应的权限
        SysUser user = (SysUser) WebUtils.getSession().getAttribute("user");
        List<SysPermission> list = this.permissionMapper.getByUserId(user.getId());
        //将list转成TreeNode
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (SysPermission sysPermission : list) {
            if(sysPermission.getPid().equals(0)){
                TreeNode parent = new TreeNode();
                BeanUtils.copyProperties(sysPermission, parent);
                treeNodeList.add(parent);
                List<TreeNode> children = new ArrayList<>();
                for (SysPermission permission : list) {
                    if(parent.getId().equals(permission.getPid())){
                        TreeNode child = new TreeNode();
                        BeanUtils.copyProperties(permission, child);
                        children.add(child);
                    }
                }
                parent.setChildren(children);
            }
        }
        return new DataGridView(treeNodeList);
    }

}

