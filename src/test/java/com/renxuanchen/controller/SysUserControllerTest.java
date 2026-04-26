package com.renxuanchen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renxuanchen.HrApplication;
import com.renxuanchen.entity.SysUser;
import com.renxuanchen.security.JwtUtils;
import com.renxuanchen.service.SysUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * SysUserController 集成测试
 */
@SpringBootTest(classes = HrApplication.class)
@AutoConfigureMockMvc
@Transactional
class SysUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysUserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() {
        // 生成测试用 JWT Token

    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"loginname\":\"admin\",\"password\":\"123456\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUsersWithAuth() throws Exception {
        mockMvc.perform(get("/user/list")
                        .header("Authorization", "Bearer " + adminToken)
                        .param("page", "1")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists());
    }

    @Test
    void testGetUsersWithoutAuth() throws Exception {
        mockMvc.perform(get("/user/list")
                        .param("page", "1")
                        .param("limit", "10"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAddUser() throws Exception {
        SysUser newUser = new SysUser();
        newUser.setLoginname("newtestuser");
        newUser.setName("新测试用户");
        newUser.setPassword("123456");
        newUser.setEmail("newtest@company.com");
        newUser.setAvailable(1);
        newUser.setSex(1);

        mockMvc.perform(post("/user/addUser")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testUpdateUser() throws Exception {
        // 先添加用户
        SysUser newUser = new SysUser();
        newUser.setLoginname("updatetestuser");
        newUser.setName("更新测试用户");
        newUser.setPassword("123456");
        newUser.setEmail("updatetest@company.com");
        newUser.setAvailable(1);
        userService.save(newUser);

        // 更新用户
        newUser.setName("更新后名称");
        newUser.setTelephone("13900000000");

        mockMvc.perform(post("/user/updateUser")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        // 先添加用户
        SysUser newUser = new SysUser();
        newUser.setLoginname("deletetestuser");
        newUser.setName("删除测试用户");
        newUser.setPassword("123456");
        newUser.setEmail("deletetest@company.com");
        newUser.setAvailable(1);
        userService.save(newUser);

        // 删除用户
        mockMvc.perform(post("/user/deleteUser")
                        .header("Authorization", "Bearer " + adminToken)
                        .param("id", String.valueOf(newUser.getId())))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserInfo() throws Exception {
        mockMvc.perform(get("/user/info/1")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void testGetUserInfoNotFound() throws Exception {
        mockMvc.perform(get("/user/info/99999")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500));
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/user/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetUserTree() throws Exception {
        mockMvc.perform(get("/user/tree")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }
}