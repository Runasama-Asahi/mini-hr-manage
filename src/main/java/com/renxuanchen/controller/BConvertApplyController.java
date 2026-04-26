package com.renxuanchen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renxuanchen.common.DataGridView;
import com.renxuanchen.common.PageModel;
import com.renxuanchen.common.ResultObj;
import com.renxuanchen.entity.BConvertApply;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.SysUserMapper;
import com.renxuanchen.security.AuthService;
import com.renxuanchen.service.BConvertApplyService;
import com.renxuanchen.vo.BConvertApplyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  转正申请控制器
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/convertApply")
public class BConvertApplyController {

    @Autowired
    private BConvertApplyService convertApplyService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private AuthService authService;

    /**
     * 加载所有转正申请
     */
    @RequestMapping("/loadAllconvertApply")
    public DataGridView loadAllconvertApply(PageModel pageModel){
        Page<BConvertApply> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        Page<BConvertApply> resultPage = this.convertApplyService.page(page);
        List<BConvertApplyVO> list = new ArrayList<>();
        for (BConvertApply record : resultPage.getRecords()) {
            BConvertApplyVO vo = new BConvertApplyVO();
            BeanUtils.copyProperties(record, vo);
            vo.setApplyName(this.userMapper.getUserNameById(record.getApplyUserId()));
            vo.setApprovalName(this.userMapper.getUserNameById(record.getApprovalUserId()));
            list.add(vo);
        }
        return new DataGridView(resultPage.getTotal(), list);
    }
    
    /**
     * 加载当前用户的转正申请
     */
    @RequestMapping("/loadUserconvertApply")
    public DataGridView loadUserconvertApply(PageModel pageModel){
        // 获取当前登录用户
        SysUser currentUser = authService.getCurrentUser();
        Integer currentUserId = currentUser.getId();

        // 创建分页对象
        Page<BConvertApply> page = new Page<>(pageModel.getPage(), pageModel.getLimit());

        // 使用 LambdaQueryWrapper 来添加查询条件，只查询当前用户的申请
        LambdaQueryWrapper<BConvertApply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BConvertApply::getApplyUserId, currentUserId); // 添加条件：申请用户ID等于当前用户ID

        // 查询分页结果
        Page<BConvertApply> resultPage = this.convertApplyService.page(page, queryWrapper);

        // 处理查询结果
        List<BConvertApplyVO> list = new ArrayList<>();
        for (BConvertApply record : resultPage.getRecords()) {
            BConvertApplyVO vo = new BConvertApplyVO();
            BeanUtils.copyProperties(record, vo);
            vo.setApplyName(this.userMapper.getUserNameById(record.getApplyUserId()));
            vo.setApprovalName(this.userMapper.getUserNameById(record.getApprovalUserId()));
            list.add(vo);
        }

        // 返回数据
        return new DataGridView(resultPage.getTotal(), list);
    }

    /**
     * 添加转正申请
     */
    @RequestMapping("/addConvertApply")
    public ResultObj addConvertApply(BConvertApply convertApply){
        convertApply.setStatus(0);
        SysUser user = authService.getCurrentUser();
        convertApply.setApplyUserId(user.getId());
        boolean save = this.convertApplyService.save(convertApply);
        if(save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    /**
     * 审核转正申请
     */
    @RequestMapping("/updateConvertApply")
    public ResultObj updateConvertApply(BConvertApply convertApply){
        convertApply.setApprovalDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        SysUser user = authService.getCurrentUser();
        //不能自己审核自己
        if(user.getId().equals(convertApply.getApplyUserId())) return ResultObj.APPROVAL_ALREADY_ERROR;
        convertApply.setApprovalUserId(user.getId());
        boolean updateById = this.convertApplyService.updateById(convertApply);
        if(updateById) return ResultObj.APPROVAL_SUCCESS;
        return ResultObj.APPROVAL_ERROR;
    }
}

