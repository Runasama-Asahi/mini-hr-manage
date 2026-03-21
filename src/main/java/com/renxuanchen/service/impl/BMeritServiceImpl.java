package com.renxuanchen.service.impl;

import com.renxuanchen.entity.BMerit;
import com.renxuanchen.mapper.BMeritMapper;
import com.renxuanchen.mapper.SysUserMapper;
import com.renxuanchen.service.BMeritService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renxuanchen.vo.BMeritVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2024-03-17
 */
@Service
@Transactional
public class BMeritServiceImpl extends ServiceImpl<BMeritMapper, BMerit> implements BMeritService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<BMeritVO> loadAll() {
        // 查询所有考核记录
        List<BMerit> merits = this.list();

        // 转换为 VO 列表
        return merits.stream()
                .map(merit -> {
                    BMeritVO vo = new BMeritVO();
                    BeanUtils.copyProperties(merit, vo);
                    vo.setUserName(sysUserMapper.getUserNameById(Math.toIntExact(merit.getUid())));
                    return vo;
                })
                .collect(Collectors.toList());
    }

}
