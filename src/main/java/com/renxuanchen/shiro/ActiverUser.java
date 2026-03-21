package com.renxuanchen.shiro;

import com.renxuanchen.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiverUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private SysUser user;
    private List<String> roles;
    private List<String> permissions;
}
