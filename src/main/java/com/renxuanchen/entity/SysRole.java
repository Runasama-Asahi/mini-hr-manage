package com.renxuanchen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

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
    public class SysRole implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 角色ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 角色名称
     */
      private String name;

      /**
     * 角色备注
     */
      private String remark;

      /**
     * 可用状态
     */
      private Integer available;

      /**
     * 创建时间
     */
      @TableField(fill = FieldFill.INSERT)
      private Date createTime;


}
