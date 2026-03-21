package com.renxuanchen.mapper;

import com.renxuanchen.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select({"select p.id,p.pid,p.title,p.icon,p.href,p.open,p.available,p.type from sys_role_user ru,sys_role_permission rp,sys_permission p where ru.rid = rp.rid and rp.pid = p.id and ru.uid = #{id} "})
    public List<SysPermission> getByUserId(Integer id);

}
