package com.renxuanchen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renxuanchen.common.DataGridView;
import com.renxuanchen.common.DeptPageModel;
import com.renxuanchen.common.ResultObj;
import com.renxuanchen.common.TreeNode;
import com.renxuanchen.entity.SysDept;
import com.renxuanchen.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService deptService;

    @RequestMapping("/loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(){
        List<SysDept> deptList = this.deptService.list();
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (SysDept sysDept : deptList) {
            TreeNode treeNode = new TreeNode(sysDept.getId(), sysDept.getPid(), sysDept.getTitle(), false);
            treeNodeList.add(treeNode);
        }
        return new DataGridView(treeNodeList);
    }

    @RequestMapping("/loadAllDept")
    public DataGridView loadAllDept(DeptPageModel pageModel){
        Page<SysDept> page = new Page<>(pageModel.getPage(),pageModel.getLimit());
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(pageModel.getId()!=null, "id", pageModel.getId());
        Page<SysDept> resultPage = this.deptService.page(page, queryWrapper);
        return new DataGridView(resultPage.getTotal(), resultPage.getRecords());
    }

    @RequestMapping("/addDept")
    public ResultObj addDept(SysDept dept){
        boolean save = this.deptService.save(dept);
        if(save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    @RequestMapping("/updateDept")
    public ResultObj updateDept(SysDept dept){
        boolean updateById = this.deptService.updateById(dept);
        if(updateById) return ResultObj.UPDATE_SUCCESS;
        return ResultObj.UPDATE_ERROR;
    }

    @RequestMapping("/deleteDept")
    public ResultObj deleteDept(Integer id){
        boolean removeById = this.deptService.removeById(id);
        if(removeById) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    @RequestMapping("/checkDeptHasChildrenNode")
    public Map<String, Boolean> checkDeptHasChildrenNode(Integer id){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        List<SysDept> deptList = this.deptService.list(queryWrapper);
        Map<String, Boolean> map = new HashMap<>();
        if(deptList.size() > 0){
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }

}

