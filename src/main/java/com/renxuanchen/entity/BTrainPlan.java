package com.renxuanchen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
    public class BTrainPlan implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 培训主题
     */
      private String title;

      /**
     * 培训内容
     */
      private String content;

      /**
     * 参与者
     */
      private String participant;

      /**
     * 培训时间
     */
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date trainDate;


}
