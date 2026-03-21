package com.renxuanchen.service;

import com.renxuanchen.entity.BMerit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.renxuanchen.vo.BMeritVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
public interface BMeritService extends IService<BMerit> {
    List<BMeritVO> loadAll();

}
