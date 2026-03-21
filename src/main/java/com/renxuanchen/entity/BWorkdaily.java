package com.renxuanchen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
    public class BWorkdaily implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户ID
     */
      private Long uid;

      /**
     * 工作完成度
     */
      private Integer progress;

      /**
     * 工作日期
     */
      private String workdate;

      /**
     * 工作月份
     */
      private String workmonth;

      /**
     * 创建时间
     */
      private LocalDateTime createtime;

      /**
     * 更新时间
     */
      private LocalDateTime updatetime;

      /**
     * 工作内容
     */
      private String workcontent;


}
