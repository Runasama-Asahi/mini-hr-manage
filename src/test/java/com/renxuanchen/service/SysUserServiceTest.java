package com.renxuanchen.service;

import com.renxuanchen.HrApplication;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.mapper.SysUserMapper;
import com.renxuanchen.service.impl.SysUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SysUserService 单元测试
 */
@SpringBootTest(classes = HrApplication.class)
@Transactional
class SysUserServiceTest {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserMapper userMapper;

    @Test
    void testGetUserTree() {
        List<?> tree = userService.getUserTree();
        assertNotNull(tree);
    }

    @Test
    void testGetByEmail() {
        SysUser user = userService.getByEmail("admin@company.com");
        assertNotNull(user);
        assertEquals("admin@company.com", user.getEmail());
    }

    @Test
    void testGetByEmailNotFound() {
        SysUser user = userService.getByEmail("notexist@company.com");
        assertNull(user);
    }

    @Test
    void testSaveUser() {
        SysUser newUser = new SysUser();
        newUser.setLoginname("testuser");
        newUser.setName("测试用户");
        newUser.setPassword("123456");
        newUser.setEmail("test@company.com");
        newUser.setAvailable(1);
        newUser.setSex(1);
        newUser.setDeptid(2);

        boolean result = userService.save(newUser);
        assertTrue(result);
        assertNotNull(newUser.getId());
    }

    @Test
    void testUpdateUser() {
        // 先创建用户
        SysUser newUser = new SysUser();
        newUser.setLoginname("updatetest");
        newUser.setName("更新测试");
        newUser.setPassword("123456");
        newUser.setEmail("update@company.com");
        newUser.setAvailable(1);
        newUser.setSex(1);
        userService.save(newUser);

        // 更新用户
        newUser.setName("更新后名称");
        newUser.setTelephone("13900000000");
        boolean result = userService.updateById(newUser);
        assertTrue(result);

        // 验证更新
        SysUser updated = userService.getById(newUser.getId());
        assertEquals("更新后名称", updated.getName());
        assertEquals("13900000000", updated.getTelephone());
    }

    @Test
    void testDeleteUser() {
        // 先创建用户
        SysUser newUser = new SysUser();
        newUser.setLoginname("deletetest");
        newUser.setName("删除测试");
        newUser.setPassword("123456");
        newUser.setEmail("delete@company.com");
        newUser.setAvailable(1);
        userService.save(newUser);

        Integer id = newUser.getId();

        // 删除用户
        boolean result = userService.removeById(id);
        assertTrue(result);

        // 验证删除（逻辑删除）
        SysUser deleted = userService.getById(id);
        assertNull(deleted);
    }

    @Test
    void testListUsers() {
        List<SysUser> users = userService.list();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    void testListAvailableUsers() {
        List<SysUser> users = userService.list()
                .stream()
                .filter(u -> u.getAvailable() == 1)
                .toList();
        assertNotNull(users);
        for (SysUser user : users) {
            assertEquals(1, user.getAvailable());
        }
    }
}