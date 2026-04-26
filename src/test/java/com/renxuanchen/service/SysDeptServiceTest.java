package com.renxuanchen.service;

import com.renxuanchen.HrApplication;
import com.renxuanchen.entity.SysDept;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SysDeptService 单元测试
 */
@SpringBootTest(classes = HrApplication.class)
@Transactional
class SysDeptServiceTest {

    @Autowired
    private SysDeptService deptService;

    @Test
    void testListDepts() {
        List<SysDept> depts = deptService.list();
        assertNotNull(depts);
        assertTrue(depts.size() > 0);
    }

    @Test
    void testGetDeptById() {
        SysDept dept = deptService.getById(1);
        assertNotNull(dept);
        assertEquals(1, dept.getId());
    }

    @Test
    void testGetDeptByIdNotFound() {
        SysDept dept = deptService.getById(99999);
        assertNull(dept);
    }
}