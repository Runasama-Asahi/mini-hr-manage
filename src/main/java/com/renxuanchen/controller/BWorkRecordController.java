package com.renxuanchen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renxuanchen.common.DataGridView;
import com.renxuanchen.common.PageModel;
import com.renxuanchen.common.ResultObj;
import com.renxuanchen.common.WorkStatusEnum;
import com.renxuanchen.entity.BWorkRecord;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.BWorkRecordMapper;
import com.renxuanchen.security.AuthService;
import com.renxuanchen.service.BWorkRecordService;
import com.renxuanchen.vo.BWorkRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  工作记录控制器
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/workRecord")
public class BWorkRecordController {

    @Autowired
    private BWorkRecordService workRecordService;
    @Autowired
    private BWorkRecordMapper workRecordMapper;
    @Autowired
    private AuthService authService;
    @Value("${work-up-time}")
    private Integer upTime;
    @Value("${work-down-time}")
    private Integer downTime;
    @Value("${work-over-time}")
    private Integer overTime;

    /**
     * 加载所有工作记录
     */
    @RequestMapping("/loadAllWorkRecord")
    public DataGridView loadAllWorkRecord(PageModel pageModel){
        Page<BWorkRecord> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        Page<BWorkRecord> resultPage = this.workRecordService.page(page);
        List<BWorkRecordVO> list = new ArrayList<>();
        for (BWorkRecord record : resultPage.getRecords()) {
            BWorkRecordVO vo = new BWorkRecordVO();
            BeanUtils.copyProperties(record, vo);
            vo.setName(this.workRecordMapper.getUserNameById(record.getId()));
            list.add(vo);
        }
        return new DataGridView(resultPage.getTotal(), list);
    }

    /**
     * 添加工作记录（打卡）
     */
    @RequestMapping("/addWorkRecord")
    public ResultObj addWorkRecord(BWorkRecord workRecord){
        // 获取当前登录用户
        SysUser user = authService.getCurrentUser();
        workRecord.setUid(user.getId());
        //判断上班或下班
        LambdaQueryWrapper<BWorkRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BWorkRecord::getUid, workRecord.getUid())
                .eq(BWorkRecord::getWorkDate, workRecord.getWorkDate());
        BWorkRecord one = this.workRecordService.getOne(queryWrapper);
        if(one == null){
            //上班
            //判断是否迟到
            Integer workRecordUpTime = Integer.valueOf(workRecord.getUpTime().substring(0, 2));
            if(workRecordUpTime > upTime || (workRecordUpTime == upTime && (Integer.valueOf(workRecord.getUpTime().substring(3, 5))) > 0)){
                workRecord.setStatus(WorkStatusEnum.LATE.getCode());
            } else {
                workRecord.setStatus(WorkStatusEnum.NORMAL.getCode());
            }
            boolean save = this.workRecordService.save(workRecord);
            if(save) return ResultObj.ADD_WORK_SUCCESS;
            return ResultObj.ADD_WORK_ERROR;
        } else {
            //判断当天是否已完成打卡
            if (one.getDownTime()!=null) {
                return ResultObj.ADD_WORK_ERROR_1;
            }
            //下班
            one.setDownTime(workRecord.getUpTime());
            //判断是否早退或加班
            Integer workRecordDownTime = Integer.valueOf(workRecord.getUpTime().substring(0, 2));
            boolean isLeaveEarly = workRecordDownTime < downTime;

            // 获取当前记录的上班状态
            int currentStatus = one.getStatus();
            // 组合状态判断
            if (currentStatus == WorkStatusEnum.LATE.getCode() && isLeaveEarly) {
                one.setStatus(WorkStatusEnum.LATE_AND_LEAVE.getCode());
            } else if (isLeaveEarly) {
                one.setStatus(WorkStatusEnum.LEAVE.getCode());
            } else if (workRecordDownTime >= overTime) {
                one.setStatus(WorkStatusEnum.OVERTIME.getCode());
            }
            boolean updateById = this.workRecordService.updateById(one);
            if(updateById) return ResultObj.ADD_WORK_SUCCESS;
            return ResultObj.ADD_WORK_ERROR;
        }
    }
}

