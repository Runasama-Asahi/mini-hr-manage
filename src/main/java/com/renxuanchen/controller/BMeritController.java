package com.renxuanchen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.ui.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renxuanchen.common.DataGridView;
import com.renxuanchen.common.PageModel;
import com.renxuanchen.common.ResultObj;
import com.renxuanchen.entity.BMerit;
import com.renxuanchen.entity.BSalaryRecord;
import com.renxuanchen.mapper.SysUserMapper;
import com.renxuanchen.service.BMeritService;
import com.renxuanchen.service.BSalaryRecordService;
import com.renxuanchen.vo.BMeritVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
@Slf4j
@RestController
@RequestMapping("/merit")
public class BMeritController {

    @Autowired
    private BMeritService meritService;

    @Autowired
    private BSalaryRecordService salaryRecordService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping("/loadAllMerit")
    public DataGridView loadAllMerit(PageModel pageModel) {
        Page<BMerit> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        Page<BMerit> resultPage = this.meritService.page(page);
        List<BMeritVO> list = new ArrayList<>();
        for (BMerit merit : resultPage.getRecords()) {
            BMeritVO vo = new BMeritVO();
            BeanUtils.copyProperties(merit, vo);
            // 查询员工姓名（需在 SysUserMapper 中实现）
            vo.setUserName(sysUserMapper.getUserNameById(Math.toIntExact(merit.getUid())));
            list.add(vo);
        }
        return new DataGridView(resultPage.getTotal(), list);
    }

    @RequestMapping("/addMerit")
    public ResultObj addMerit(BMerit merit){
        boolean save = this.meritService.save(merit);
        if(save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    @RequestMapping("/updateMerit")
    public ResultObj updateMerit(BMerit merit){
        boolean updateById = this.meritService.updateById(merit);
        if(updateById) return ResultObj.UPDATE_SUCCESS;
        return ResultObj.UPDATE_ERROR;
    }

    @RequestMapping("/deleteMerit")
    public ResultObj deleteMerit(Integer id){
        boolean remove = this.meritService.removeById(id);
        if(remove) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    @RequestMapping("/batchDeleteMerit")
    public ResultObj batchDeleteMerit(Integer[] ids){
        List<Integer> idList = Arrays.asList(ids);
        boolean removeByIds = this.meritService.removeByIds(idList);
        if(removeByIds) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    @RequestMapping("/completeMerit")
    public ResultObj completeMerit(Integer meritId) {
        BMerit merit = meritService.getById(meritId);
        if (merit == null) { // 添加空检查
            return ResultObj.OPERATE_ERROR.setMsg("考核记录不存在");
        }
        if (merit != null && merit.getIsCompleted() == 0) {
            merit.setIsCompleted(1);
            boolean update = meritService.updateById(merit);
            if (update) {
                // 自动触发工资更新（调用工资服务）
                updateSalaryForMerit(merit.getUid(), merit.getMonth());
                return ResultObj.OPERATE_SUCCESS;
            }
        }
        return ResultObj.OPERATE_ERROR;
    }

    private void updateSalaryForMerit(Long uid, String month) {
        LambdaQueryWrapper<BSalaryRecord> query = new LambdaQueryWrapper<>();
        query.eq(BSalaryRecord::getUid, uid).eq(BSalaryRecord::getSalaryMonth, month);
        BSalaryRecord salary = salaryRecordService.getOne(query);
        if (salary != null) {
            salary.setMeritsAmount(1000f); // 固定奖金1000
            salaryRecordService.updateById(salary);
        }
    }
    @RequestMapping("/meritManager")
    public String meritManager(Model model) {
        List<BMeritVO> list = meritService.loadAll();
        model.addAttribute("meritList", list); // 确保传递了模板所需变量
        return "meritManager";
    }

    @RequestMapping("/saveMerit")
    public ResultObj saveMerit(@RequestBody BMerit merit) {
        // 根据考核量自动计算是否完成
        merit.setIsCompleted(merit.getCurrentAmount() >= merit.getMonthQuota() ? 1 : 0);
        boolean success = meritService.saveOrUpdate(merit);
        return success ? ResultObj.success() : ResultObj.error("保存失败");
    }
    @RequestMapping("/save")
    public ResultObj save(@RequestBody BMerit merit) {
        try {
            // 自动计算完成状态
            merit.setIsCompleted(merit.getCurrentAmount() >= merit.getMonthQuota() ? 1 : 0);

            // 字段校验
            if (merit.getUid() == null) {
                return ResultObj.error("必须选择员工");
            }
            if (merit.getMonth() == null || !merit.getMonth().matches("\\d{4}-\\d{2}")) {
                return ResultObj.error("月份格式错误");
            }

            // 保存或更新
            boolean success = merit.getId() == null ?
                    meritService.save(merit) :
                    meritService.updateById(merit);

            // 同步更新工资
            if (success && merit.getIsCompleted() == 1) {
                updateSalaryForMerit(merit.getUid(), merit.getMonth());
            }

            return success ? ResultObj.success() : ResultObj.error("操作失败");
        } catch (Exception e) {
            log.error("考核操作异常", e);
            return ResultObj.error("系统异常");
        }
    }

}

