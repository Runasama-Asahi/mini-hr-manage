package com.renxuanchen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renxuanchen.common.DataGridView;
import com.renxuanchen.common.ResultObj;
import com.renxuanchen.common.SalaryPageModel;
import com.renxuanchen.entity.BMerit;
import com.renxuanchen.entity.BSalaryRecord;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.BSalaryRecordMapper;
import com.renxuanchen.security.AuthService;
import com.renxuanchen.service.BMeritService;
import com.renxuanchen.service.BSalaryRecordService;
import com.renxuanchen.vo.BSalaryRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  工资记录控制器
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
    @Autowired
    private AuthService authService;

    /**
     * 加载所有工资记录
     */
    @RequestMapping("/loadAllSalaryRecord")
    public DataGridView loadAllSalaryRecord(SalaryPageModel pageModel) {
        Page<BSalaryRecord> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        LambdaQueryWrapper<BSalaryRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(pageModel.getSalaryMonth()), BSalaryRecord::getSalaryMonth, pageModel.getSalaryMonth());
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

    /**
     * 加载当前用户的工资记录
     */
    @RequestMapping("/loadUserSalaryRecord")
    public DataGridView loadUserSalaryRecord(SalaryPageModel pageModel) {
        // 获取当前登录用户
        SysUser currentUser = authService.getCurrentUser();
        Integer currentUserId = currentUser.getId();

        Page<BSalaryRecord> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        LambdaQueryWrapper<BSalaryRecord> queryWrapper = new LambdaQueryWrapper<>();

        // 添加条件：只查询当前用户的工资记录
        queryWrapper.eq(BSalaryRecord::getUid, currentUserId);

        // 如果工资月份不为空，则添加相应的条件
        queryWrapper.eq(StringUtils.isNotBlank(pageModel.getSalaryMonth()), BSalaryRecord::getSalaryMonth, pageModel.getSalaryMonth());

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

    /**
     * 添加工资记录
     */
    @RequestMapping("/addSalaryRecord")
    public ResultObj addSalaryRecord(BSalaryRecord record) {
        // 查询员工的考核状态
        LambdaQueryWrapper<BMerit> query = new LambdaQueryWrapper<>();
        query.eq(BMerit::getUid, record.getUid())
                .eq(BMerit::getMonth, record.getSalaryMonth());
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

    /**
     * 更新工资记录
     */
    @RequestMapping("/updateSalaryRecord")
    public ResultObj updateSalaryRecord(BSalaryRecord bSalaryRecord) {
        // 根据考核状态自动计算绩效奖金
        LambdaQueryWrapper<BMerit> query = new LambdaQueryWrapper<>();
        query.eq(BMerit::getUid, bSalaryRecord.getUid())
                .eq(BMerit::getMonth, bSalaryRecord.getSalaryMonth());
        BMerit merit = meritService.getOne(query);

        if (merit != null && merit.getIsCompleted() == 1) {
            bSalaryRecord.setMeritsAmount(1000f); // 完成则奖金1000
        } else {
            bSalaryRecord.setMeritsAmount(0f); // 未完成则为0
        }

        boolean update = salaryRecordService.updateById(bSalaryRecord);
        return update ? ResultObj.UPDATE_SUCCESS : ResultObj.UPDATE_ERROR;
    }

    /**
     * 删除工资记录
     */
    @RequestMapping("/deleteSalaryRecord")
    public ResultObj deleteSalaryRecord(Integer id){
        boolean removeById = this.salaryRecordService.removeById(id);
        if(removeById) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    /**
     * 批量删除工资记录
     */
    @RequestMapping("/batchDeleteSalaryRecord")
    public ResultObj batchDeleteSalaryRecord(Integer[] ids){
        List<Integer> idList = Arrays.asList(ids);
        boolean removeByIds = this.salaryRecordService.removeByIds(idList);
        if(removeByIds) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }
}

