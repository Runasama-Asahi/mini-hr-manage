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
 * 角色用户关联表
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_user")
public class SysRoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "rid", type = IdType.INPUT)
    private Integer rid;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Integer uid;

}
