package com.renxuanchen.entity;

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
    public class SysRoleUser implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 角色ID
     */
        private Integer rid;

      /**
     * 用户ID
     */
      private Integer uid;


}
