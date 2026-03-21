package com.renxuanchen.mapper;

import com.renxuanchen.entity.SysUser;
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
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select({"select u.name from sys_user u where u.id = #{id} "})
    public String getUserNameById(Integer id);
}
