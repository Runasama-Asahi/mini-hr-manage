package com.renxuanchen.mapper;

import com.renxuanchen.entity.BSalaryRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
public interface BSalaryRecordMapper extends BaseMapper<BSalaryRecord> {

    @Select({"select u.name from sys_user u,b_salary_record s where s.uid = u.id and s.id = #{id} "})
    public String getUserNameById(Integer id);

    @Select({"select d.title from sys_user u,b_salary_record s,sys_dept d where s.uid = u.id and u.deptid = d.id and s.id = #{id} "})
    public String getDeptNameById(Integer id);

}
