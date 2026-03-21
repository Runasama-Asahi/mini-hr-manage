package com.renxuanchen.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renxuanchen.common.DataGridView;
import com.renxuanchen.common.PageModel;
import com.renxuanchen.common.ResultObj;
import com.renxuanchen.entity.BTrainPlan;
import com.renxuanchen.service.BTrainPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/trainPlan")
public class BTrainPlanController {

    @Autowired
    private BTrainPlanService trainPlanService;

    @RequestMapping("/loadAlltrainPlan")
    public DataGridView loadAlltrainPlan(PageModel pageModel){
        Page<BTrainPlan> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        Page<BTrainPlan> resultPage = this.trainPlanService.page(page);
        return new DataGridView(resultPage.getTotal(), resultPage.getRecords());
    }

    @RequestMapping("/addtrainPlan")
    public ResultObj addtrainPlan(BTrainPlan trainPlan){
        boolean save = this.trainPlanService.save(trainPlan);
        if(save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    @RequestMapping("/updateTrainPlan")
    public ResultObj updateTrainPlan(BTrainPlan trainPlan){
        boolean updateById = this.trainPlanService.updateById(trainPlan);
        if(updateById) return ResultObj.UPDATE_SUCCESS;
        return ResultObj.UPDATE_ERROR;
    }

    @RequestMapping("/deleteTrainPlan")
    public ResultObj deleteTrainPlan(Integer id){
        boolean removeById = this.trainPlanService.removeById(id);
        if(removeById) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    @RequestMapping("/batchDeleteTrainPlan")
    public ResultObj batchDeleteTrainPlan(Integer[] ids){
        List<Integer> idList = Arrays.asList(ids);
        boolean removeByIds = this.trainPlanService.removeByIds(idList);
        if(removeByIds) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

}

