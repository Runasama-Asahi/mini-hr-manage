package com.renxuanchen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renxuanchen.common.DataGridView;
import com.renxuanchen.common.ResultObj;
import com.renxuanchen.common.SalaryPageModel;
import com.renxuanchen.entity.BMerit;
import com.renxuanchen.entity.BSalaryRecord;
import com.renxuanchen.mapper.BSalaryRecordMapper;
import com.renxuanchen.service.BMeritService;
import com.renxuanchen.service.BSalaryRecordService;
import com.renxuanchen.shiro.ActiverUser;
import com.renxuanchen.vo.BSalaryRecordVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/salaryRecord")
public class BSalaryRecordController {
    @Autowired
    private BMeritService meritService;

    @Autowired
    private BSalaryRecordService salaryRecordService;
    @Autowired
    private BSalaryRecordMapper salaryRecordMapper;

    @RequestMapping("/loadAllSalaryRecord")
    public DataGridView loadAllSalaryRecord(SalaryPageModel pageModel) {
        Page<BSalaryRecord> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        QueryWrapper<BSalaryRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(pageModel.getSalaryMonth()), "salary_month", pageModel.getSalaryMonth());
        Page<BSalaryRecord> resultPage = this.salaryRecordService.page(page, queryWrapper);
        List<BSalaryRecordVO> list = new ArrayList<>();
        for (BSalaryRecord record : resultPage.getRecords()) {
            BSalaryRecordVO vo = new BSalaryRecordVO();
            BeanUtils.copyProperties(record, vo);
            vo.setName(this.salaryRecordMapper.getUserNameById(record.getId()));
            vo.setDeptname(this.salaryRecordMapper.getDeptNameById(record.getId()));
            list.add(vo);
        }
        DataGridView dataGridView = new DataGridView(resultPage.getTotal(), list);
        return dataGridView;
    }

    @RequestMapping("/loadUserSalaryRecord")
    public DataGridView loadUserSalaryRecord(SalaryPageModel pageModel) {
        // 获取当前用户
        ActiverUser currentUser = (ActiverUser) SecurityUtils.getSubject().getPrincipal();

        // 从 ActiverUser 中获取 SysUser 对象，然后获取用户 ID
        Integer currentUserId = currentUser.getUser().getId(); // 通过 getUser() 获取 SysUser 对象，再获取 ID

        Page<BSalaryRecord> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        QueryWrapper<BSalaryRecord> queryWrapper = new QueryWrapper<>();

        // 添加条件：只查询当前用户的工资记录
        queryWrapper.eq("uid", currentUserId); // 假设uid是用户的ID字段

        // 如果工资月份不为空，则添加相应的条件
        queryWrapper.eq(StringUtils.isNotBlank(pageModel.getSalaryMonth()), "salary_month", pageModel.getSalaryMonth());

        Page<BSalaryRecord> resultPage = this.salaryRecordService.page(page, queryWrapper);
        List<BSalaryRecordVO> list = new ArrayList<>();

        for (BSalaryRecord record : resultPage.getRecords()) {
            BSalaryRecordVO vo = new BSalaryRecordVO();
            BeanUtils.copyProperties(record, vo);
            vo.setName(this.salaryRecordMapper.getUserNameById(record.getId()));
            vo.setDeptname(this.salaryRecordMapper.getDeptNameById(record.getId()));
            list.add(vo);
        }

        DataGridView dataGridView = new DataGridView(resultPage.getTotal(), list);
        return dataGridView;
    }

    @RequestMapping("/addSalaryRecord")
    public ResultObj addSalaryRecord(BSalaryRecord record) {
        // 查询员工的考核状态
        QueryWrapper<BMerit> query = new QueryWrapper<>();
        query.eq("uid", record.getUid())
                .eq("month", record.getSalaryMonth());
        BMerit merit = meritService.getOne(query);

        // 自动计算绩效奖金
        if (merit != null && merit.getIsCompleted() == 1) {
            record.setMeritsAmount(1000f);
        } else {
            record.setMeritsAmount(0f);
        }

        boolean save = salaryRecordService.save(record);
        return save ? ResultObj.ADD_SUCCESS : ResultObj.ADD_ERROR;
    }


    @RequestMapping("/updateSalaryRecord")
    public ResultObj updateSalaryRecord(BSalaryRecord bSalaryRecord) {
        // 根据考核状态自动计算绩效奖金
        QueryWrapper<BMerit> query = new QueryWrapper<>();
        query.eq("uid", bSalaryRecord.getUid())
                .eq("month", bSalaryRecord.getSalaryMonth());
        BMerit merit = meritService.getOne(query);

        if (merit != null && merit.getIsCompleted() == 1) {
            bSalaryRecord.setMeritsAmount(1000f); // 完成则奖金1000
        } else {
            bSalaryRecord.setMeritsAmount(0f); // 未完成则为0
        }

        boolean update = salaryRecordService.updateById(bSalaryRecord);
        return update ? ResultObj.UPDATE_SUCCESS : ResultObj.UPDATE_ERROR;
    }

    @RequestMapping("/deleteSalaryRecord")
    public ResultObj deleteSalaryRecord(Integer id){
        boolean removeById = this.salaryRecordService.removeById(id);
        if(removeById) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    @RequestMapping("/batchDeleteSalaryRecord")
    public ResultObj batchDeleteSalaryRecord(Integer[] ids){
        List<Integer> idList = Arrays.asList(ids);
        boolean removeByIds = this.salaryRecordService.removeByIds(idList);
        if(removeByIds) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

}

