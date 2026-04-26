package com.renxuanchen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_permission")
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 角色ID
     */
    @TableId(value = "rid", type = IdType.INPUT)
    private Integer rid;

    /**
     * 菜单或权限ID
     */
    @TableField("pid")
    private Integer pid;


}
