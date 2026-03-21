package com.renxuanchen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

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
    public class BMerit implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;
  /**
   * 当前完成量
   */
  private Integer currentAmount = 0;



  /**
   * 员工ID（关联SysUser）
   */
  private Long uid;

  /**
   * 月考核指标（默认50）
   */
  private Integer monthQuota = 50;

  /**
   * 是否完成（0未完成，1完成）
   */
  private Integer isCompleted = 0;


  /**
   * 考核月份（格式：yyyy-MM）
   */
  @TableField("month")
  private String month;

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }
}
