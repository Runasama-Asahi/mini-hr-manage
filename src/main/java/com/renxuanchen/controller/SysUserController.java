package com.renxuanchen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renxuanchen.common.*;
import com.renxuanchen.entity.SysRole;
import com.renxuanchen.entity.SysRoleUser;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.SysDeptMapper;
import com.renxuanchen.service.SysDeptService;
import com.renxuanchen.service.SysRoleService;
import com.renxuanchen.service.SysRoleUserService;
import com.renxuanchen.service.SysUserService;
import com.renxuanchen.vo.SysUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysDeptService deptService;
    @Autowired
    private SysRoleUserService roleUserService;

    @RequestMapping("/listm")
    public ResultObj getUsers() {
        List<Map<String, Object>> users = userService.list()
                .stream()
                .filter(u -> u.getAvailable() == 1)
                .map(u -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", u.getId());    // 确保字段名为id
                    map.put("name", u.getName());
                    return map;
                }).collect(Collectors.toList());
        return ResultObj.success(users);
    }

    @RequestMapping("/tree")
    public ResultObj getUserTree() {
        List<UserTreeNode> nodes = userService.list()
                .stream()
                .filter(u -> u.getAvailable() == 1)
                .map(u -> new UserTreeNode(u.getId(), u.getName()))
                .collect(Collectors.toList());
        return ResultObj.success(nodes);
    }

    @RequestMapping("/info/{id}")
    public ResultObj getUserInfo(@PathVariable Integer id) {
        SysUser user = userService.getById(id);
        return user != null ? ResultObj.success(user) : ResultObj.error("用户不存在");
    }


    // 获取所有用户列表（用于下拉选择）
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultObj getAllUsers() {
        List<SysUser> users = userService.list();
        return ResultObj.success(users);
    }

    @RequestMapping("/loadAllUser")
    public List<SysUser> loadAllUser(){
        return this.userService.list();
    }

    @RequestMapping("/list")
    public DataGridView list(PageModel pageModel){
        Page<SysUser> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        Page<SysUser> resultPage = this.userService.page(page);
        List<SysUserVO> list = new ArrayList<>();
        for (SysUser record : resultPage.getRecords()) {
            SysUserVO vo = new SysUserVO();
            BeanUtils.copyProperties(record, vo);
            vo.setDeptName(this.deptMapper.getDeptNameById(record.getDeptid()));
            list.add(vo);
        }
        return new DataGridView(resultPage.getTotal(), list);
    }

    @RequestMapping("/addUser")
    public ResultObj addUser(SysUser user){
        boolean save = this.userService.save(user);
        if(save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    @RequestMapping("/updateUser")
    public ResultObj updateUser(SysUser user){
        boolean updateById = this.userService.updateById(user);
        if(updateById) return ResultObj.UPDATE_SUCCESS;
        return ResultObj.UPDATE_ERROR;
    }

    @RequestMapping("/deleteUser")
    public ResultObj deleteUser(Integer id){
        boolean removeById = this.userService.removeById(id);
        if(removeById) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    @RequestMapping("/initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        //查询全部可用角色
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> maps = this.roleService.listMaps(queryWrapper);
        //查询当前用户所拥有的角色
        QueryWrapper<SysRoleUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("uid", id);
        List<SysRoleUser> roleUserList = this.roleUserService.list(queryWrapper1);
        for (Map<String, Object> map : maps) {//把role存储map进行遍历，然后进行判断
            Boolean LAY_CHECKED = false;
            Integer roleid = (Integer) map.get("id");
            for (SysRoleUser roleUser : roleUserList) {
                if(roleUser.getRid().equals(roleid)) LAY_CHECKED = true;
            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }
        return new DataGridView(Long.valueOf(maps.size()), maps);
    }

    @RequestMapping("/updateRole")
    public ResultObj updateRole(Integer[] ids){
        QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", ids[0]);
        this.roleUserService.remove(queryWrapper);
        List<SysRoleUser> list = new ArrayList<>();
        for (int i = 1; i < ids.length; i++) {
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setUid(ids[0]);
            roleUser.setRid(ids[i]);
            list.add(roleUser);
        }
        boolean saveBatch = this.roleUserService.saveBatch(list);
        if(saveBatch) return ResultObj.DISPATCH_SUCCESS;
        return ResultObj.DISPATCH_ERROR;
    }

    /**
     * 发送密码重置验证码
     * @param email 用户邮箱
     * @return 操作结果
     */
    // 发送验证码接口
    @PostMapping("/sendResetCode")
    public ResultObj sendResetCode(@RequestParam String email) {
        SysUser user = userService.getOne(new QueryWrapper<SysUser>().eq("email", email));
        if (user == null) {
            return new ResultObj(Constast.ERROR, "邮箱未注册");
        }

        // 生成6位随机验证码
        String code = UUID.randomUUID().toString().substring(0, 6);
        user.setRemark(code); // 临时存储验证码到备注字段
        userService.updateById(user);

        // 模拟发送邮件（实际需集成邮件服务）
        System.out.println("验证码已发送至 " + email + "，验证码为：" + code);
        return ResultObj.OPERATE_SUCCESS;
    }

    /**
     * 重置密码
     * @param email 用户邮箱
     * @param code 验证码
     * @param newPassword 新密码
     * @return 操作结果
     */
    // 重置密码接口
    @PostMapping("/resetPassword")
    public ResultObj resetPassword(
            @RequestParam String email,
            @RequestParam String code,
            @RequestParam String newPassword
    ) {
        SysUser user = userService.getOne(new QueryWrapper<SysUser>().eq("email", email));
        if (user == null) {
            return new ResultObj(Constast.ERROR, "用户不存在");
        }

        // 验证码校验
        if (!code.equals(user.getRemark())) {
            return new ResultObj(Constast.ERROR, "验证码错误");
        }

        // 更新密码
        user.setPassword(newPassword);
        user.setRemark(""); // 清空验证码
        userService.updateById(user);

        return ResultObj.OPERATE_SUCCESS;
    }


}

