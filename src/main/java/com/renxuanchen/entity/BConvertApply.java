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
    public class BConvertApply implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer applyUserId;

    private String applyDate;

    private String applyContent;

    private Integer status;

    private String approvalDate;

    private Integer approvalUserId;

    private String approvalOpinion;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
