-- Docker Compose 数据库初始化脚本
-- 复制自 hr.sql，使用现有数据库结构

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_convert_apply
-- ----------------------------
DROP TABLE IF EXISTS `b_convert_apply`;
CREATE TABLE `b_convert_apply` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
`apply_user_id` bigint NULL DEFAULT NULL,
`apply_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`apply_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`status` int NULL DEFAULT NULL,
`approval_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`approval_user_id` int NULL DEFAULT NULL,
`approval_opinion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
PRIMARY KEY (`id`) USING BTREE,
INDEX `b_convert_apply`(`apply_user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for b_merit
-- ----------------------------
DROP TABLE IF EXISTS `b_merit`;
CREATE TABLE `b_merit` (
`id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
`month_quota` int NULL DEFAULT NULL COMMENT '月考核量',
`uid` bigint NULL DEFAULT NULL COMMENT '员工ID',
`is_completed` int NULL DEFAULT 0 COMMENT '是否完成',
`month` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考核月份',
`current_amount` int NULL DEFAULT 0 COMMENT '当前完成量',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for b_salary_record
-- ----------------------------
DROP TABLE IF EXISTS `b_salary_record`;
CREATE TABLE `b_salary_record` (
`id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
`uid` bigint NULL DEFAULT NULL COMMENT '用户ID',
`salary_month` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工资月份',
`must_salary` float NULL DEFAULT NULL COMMENT '基本工资',
`reality_salary` float NULL DEFAULT NULL COMMENT '全勤工资',
`late_amount` float NULL DEFAULT NULL COMMENT '迟到扣款',
`tax_amount` float NULL DEFAULT NULL COMMENT '纳税扣款',
`merits_amount` float NULL DEFAULT NULL COMMENT '绩效奖金',
`pension_amount` float NULL DEFAULT NULL COMMENT '五险扣款',
`leave_amount` float NULL DEFAULT NULL COMMENT '请假扣款',
`create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`) USING BTREE,
INDEX `b_salary_record`(`uid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for b_train_plan
-- ----------------------------
DROP TABLE IF EXISTS `b_train_plan`;
CREATE TABLE `b_train_plan` (
`id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
`title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培训主题',
`content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培训内容',
`participant` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参与者',
`train_date` datetime NULL DEFAULT NULL COMMENT '培训时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for b_work_record
-- ----------------------------
DROP TABLE IF EXISTS `b_work_record`;
CREATE TABLE `b_work_record` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
`uid` bigint NULL DEFAULT NULL COMMENT '用户ID',
`work_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打卡日期',
`up_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上班时间',
`down_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下班时间',
`status` int NULL DEFAULT NULL COMMENT '打卡状态',
`create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
`update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
PRIMARY KEY (`id`) USING BTREE,
INDEX `b_work_record`(`uid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
`id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
`pid` int NULL DEFAULT NULL COMMENT '父级部门ID',
`title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
`remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
`address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
`available` int NULL DEFAULT NULL COMMENT '状态【0不可用1可用】',
`create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '菜单或权限ID',
`pid` int NULL DEFAULT NULL COMMENT '父级菜单或权限ID',
`title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单或权限名称',
`icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
`href` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单栏跳转路径',
`open` int NULL DEFAULT NULL COMMENT '菜单是否展开',
`available` int NULL DEFAULT NULL COMMENT '状态【0不可用1可用】',
`type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
`name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
`remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色备注',
`available` int NULL DEFAULT NULL COMMENT '可用状态',
`create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
`rid` int NOT NULL COMMENT '角色ID',
`pid` int NOT NULL COMMENT '菜单或权限ID',
PRIMARY KEY (`pid`, `rid`) USING BTREE,
INDEX `FK_tcsr9ucxypb3ce1q5qngsfk33`(`rid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
`rid` int NOT NULL COMMENT '角色ID',
`uid` bigint NOT NULL COMMENT '用户ID',
PRIMARY KEY (`uid`, `rid`) USING BTREE,
INDEX `sys_role_user_1`(`rid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
`loginname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
`name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
`password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
`sex` int NULL DEFAULT NULL COMMENT '性别',
`available` int NULL DEFAULT NULL COMMENT '是否可用',
`hiredate` datetime NULL DEFAULT NULL COMMENT '入职时间',
`deptid` int NULL DEFAULT NULL COMMENT '部门ID',
`address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
`remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
`telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
`email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_convert_apply
-- ----------------------------
INSERT INTO `b_convert_apply` VALUES (1, 3, '2022-04-15', '请假', 2, '2024-03-24', 1, '111', '2022-04-15 10:04:45', NULL);
INSERT INTO `b_convert_apply` VALUES (17, 2, '2024-03-25', '转正申请', 1, '2024-03-25', 1, '绩效未达标', '2024-03-25 12:54:33.877', '2024-03-25 12:55:12.26');
INSERT INTO `b_convert_apply` VALUES (18, 2, '2024-03-25', '转正', 2, '2024-03-25', 1, 'OK', '2024-03-25 12:55:36.728', '2024-03-25 12:55:47.262');
INSERT INTO `b_convert_apply` VALUES (21, 1, '2024-03-26', '转正', 2, '2024-03-26', 24, 'OK', '2024-03-26 16:05:04.333', '2024-03-26 16:05:52.758');
INSERT INTO `b_convert_apply` VALUES (22, 24, '2024-03-26', '转正', 1, '2024-03-26', 1, 'error', '2024-03-26 16:06:05.223', '2024-03-26 16:06:22.454');
INSERT INTO `b_convert_apply` VALUES (23, 3, '2024-10-19', '转正', 0, NULL, NULL, NULL, '2024-10-19 13:23:40.873', NULL);

-- ----------------------------
-- Records of b_merit
-- ----------------------------
INSERT INTO `b_merit` VALUES (12, 20, 2, 0, '2025-04', 1);
INSERT INTO `b_merit` VALUES (22, 1000, 26, 0, '11', 0);
INSERT INTO `b_merit` VALUES (23, 300, 27, 0, '11', 0);
INSERT INTO `b_merit` VALUES (24, 3000, 22, 0, '11', 0);
INSERT INTO `b_merit` VALUES (25, 2000, 3, 0, '11', 0);
INSERT INTO `b_merit` VALUES (54, 55, 1, 0, '11', 0);
INSERT INTO `b_merit` VALUES (56, 50, 27, 1, '2025-04', 50);
INSERT INTO `b_merit` VALUES (58, 10, 1, 0, '2025-01', 0);
INSERT INTO `b_merit` VALUES (59, 10, 22, 1, '2025-02', 12);
INSERT INTO `b_merit` VALUES (60, 20, 2, 1, '2025-05', 20);

-- ----------------------------
-- Records of b_salary_record
-- ----------------------------
INSERT INTO `b_salary_record` VALUES (1, 2, '2022-04', 5000, 555, 55, 55, 55, 0, 55, '2022-04-15 11:22:56');
INSERT INTO `b_salary_record` VALUES (22, 27, '2024-10', 4000, 500, 200, 10, 300, 600, 100, '2024-10-16 13:50:36');
INSERT INTO `b_salary_record` VALUES (23, 3, '2024-10', 5000, 500, 50, 50, 100, 2000, 100, '2024-10-19 12:44:16');
INSERT INTO `b_salary_record` VALUES (24, 2, '2025-01', 4500, 300, 100, 30, 500, 500, 50, '2025-01-20 12:38:02');
INSERT INTO `b_salary_record` VALUES (25, 27, '2025-04', 5000, 200, 50, 0, 1000, 500, 10, '2025-03-28 13:41:21');
INSERT INTO `b_salary_record` VALUES (26, 27, '2025-03', 5000, 200, 50, 0, 0, 500, 10, '2025-03-28 13:42:20');

-- ----------------------------
-- Records of b_train_plan
-- ----------------------------
INSERT INTO `b_train_plan` VALUES (12, '新员工入职培训', '业务流程', '全体新员工', '2024-03-23 10:00:00');

-- ----------------------------
-- Records of b_work_record
-- ----------------------------
INSERT INTO `b_work_record` VALUES (41, 3, '2023-11-20', '07:00:00', '14:42:18', 2, '2023-11-20 07:00:00', '2023-11-21 14:42:30');
INSERT INTO `b_work_record` VALUES (42, 2, '2022-04-19', '08:09:00', '19:08:05', 0, '2022-04-19 18:18:41', '2022-04-20 22:27:17');
INSERT INTO `b_work_record` VALUES (116, 1, '2024-03-26', '08:01:00', '21:01:00', 4, '2024-03-26 16:03:55', '2024-03-26 16:03:55');
INSERT INTO `b_work_record` VALUES (117, 26, '2024-03-26', '21:01:00', '21:01:00', 4, '2024-10-16 13:53:14', '2024-10-16 13:53:14');
INSERT INTO `b_work_record` VALUES (120, 26, '2024-10-16', '10:59:00', '10:59:00', 2, '2024-10-16 14:13:12', '2024-10-16 14:13:12');
INSERT INTO `b_work_record` VALUES (121, 27, '2025-01-20', '13:53:15', '13:54:30', 2, '2025-01-20 13:54:33', '2025-01-20 13:54:33');
INSERT INTO `b_work_record` VALUES (122, 27, '2025-03-24', '15:00:53', '15:00:57', 3, '2025-03-24 15:00:58', '2025-03-24 15:00:58');
INSERT INTO `b_work_record` VALUES (123, 1, '2026-04-10', '10:54:08', '10:54:13', 3, '2026-04-10 10:54:14', '2026-04-10 10:54:14');
INSERT INTO `b_work_record` VALUES (124, 1, '2026-04-25', '13:10:59', '15:50:34', 3, '2026-04-25 13:11:01', '2026-04-25 15:50:38');

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '总经办', '大BOSS1', '重庆', 1, '2022-04-01 14:06:32');
INSERT INTO `sys_dept` VALUES (2, 1, '生产部', '程序员', '武汉', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES (3, 1, '销售部', '无', '武汉', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES (4, 1, '财务部', '无', '武汉', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES (5, 1, '人事部', '人事部', '武汉', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES (6, 1, '管理部', '管理部', '武汉', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES (8, 3, '销售一部', '销售一部', '11', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES (9, 4, '财务一部', '财务一部', '222', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES (10, 5, '人事二部', '人事二部', '北京', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES (36, 6, '管理一部', '管理一部', '西安', 1, '2022-02-03 08:55:00');
INSERT INTO `sys_dept` VALUES (37, 2, '生产一部', '生产一部', '宝鸡', 1, '2022-02-03 08:59:08');
INSERT INTO `sys_dept` VALUES (38, 2, '生产三部', '生产三部', '深圳', 1, '2022-02-03 09:09:06');

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (2, 0, '绩效管理', '&#xe857;', '', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (3, 0, '工资管理', '&#xe65e;', '', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (4, 0, '考勤管理', '&#xe611;', '', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (5, 0, '培训管理', '&#xe628;', '', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (6, 0, '系统管理', '&#xe614;', '', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (7, 2, '考核设置', '&#xe62c;', '/meritManager', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (8, 3, '工资管理', '&#xe658;', '/salaryRecordManager', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (9, 4, '出勤记录', '&#xe637;', '/workRecordManager', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (11, 5, '培训计划', '&#xe756;', '/trainPlanManager', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (12, 6, '员工管理', '&#xe770;', '/userManager', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (13, 6, '部门管理', '&#xe770;', '/deptManager', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (14, 6, '角色管理', '&#xe770;', '/roleManager', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (118, 5, '申请审批', '&#xe756;', '/convertApplyManager', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (135, 5, '申请', '&#xe756;', '/convertApplyUser', 0, 1, 'menu');
INSERT INTO `sys_permission` VALUES (136, 3, '工资查询', '&#xe658;', '/salaryRecordUser', 0, 1, 'menu');

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '拥有所有菜单权限', 1, '2021-12-30 14:06:32');
INSERT INTO `sys_role` VALUES (2, '财务专员', '财务', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_role` VALUES (3, '人事专员', '人事', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_role` VALUES (4, '普通成员', '普通', 1, '2022-04-10 14:06:32');
INSERT INTO `sys_role` VALUES (17, '管理部专员', '管理部专员', 1, '2024-03-25 20:19:44');
INSERT INTO `sys_role` VALUES (20, 'test', 'test', 1, '2024-03-26 16:10:35');

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (1, 8);
INSERT INTO `sys_role_permission` VALUES (1, 9);
INSERT INTO `sys_role_permission` VALUES (1, 11);
INSERT INTO `sys_role_permission` VALUES (1, 12);
INSERT INTO `sys_role_permission` VALUES (1, 13);
INSERT INTO `sys_role_permission` VALUES (1, 14);
INSERT INTO `sys_role_permission` VALUES (1, 118);
INSERT INTO `sys_role_permission` VALUES (2, 3);
INSERT INTO `sys_role_permission` VALUES (2, 4);
INSERT INTO `sys_role_permission` VALUES (2, 5);
INSERT INTO `sys_role_permission` VALUES (2, 8);
INSERT INTO `sys_role_permission` VALUES (2, 9);
INSERT INTO `sys_role_permission` VALUES (2, 135);
INSERT INTO `sys_role_permission` VALUES (2, 136);
INSERT INTO `sys_role_permission` VALUES (3, 3);
INSERT INTO `sys_role_permission` VALUES (3, 4);
INSERT INTO `sys_role_permission` VALUES (3, 5);
INSERT INTO `sys_role_permission` VALUES (3, 6);
INSERT INTO `sys_role_permission` VALUES (3, 9);
INSERT INTO `sys_role_permission` VALUES (3, 12);
INSERT INTO `sys_role_permission` VALUES (3, 13);
INSERT INTO `sys_role_permission` VALUES (3, 135);
INSERT INTO `sys_role_permission` VALUES (3, 136);
INSERT INTO `sys_role_permission` VALUES (4, 3);
INSERT INTO `sys_role_permission` VALUES (4, 4);
INSERT INTO `sys_role_permission` VALUES (4, 5);
INSERT INTO `sys_role_permission` VALUES (4, 9);
INSERT INTO `sys_role_permission` VALUES (4, 135);
INSERT INTO `sys_role_permission` VALUES (4, 136);
INSERT INTO `sys_role_permission` VALUES (17, 2);
INSERT INTO `sys_role_permission` VALUES (17, 3);
INSERT INTO `sys_role_permission` VALUES (17, 4);
INSERT INTO `sys_role_permission` VALUES (17, 5);
INSERT INTO `sys_role_permission` VALUES (17, 6);
INSERT INTO `sys_role_permission` VALUES (17, 7);
INSERT INTO `sys_role_permission` VALUES (17, 9);
INSERT INTO `sys_role_permission` VALUES (17, 11);
INSERT INTO `sys_role_permission` VALUES (17, 13);
INSERT INTO `sys_role_permission` VALUES (17, 118);
INSERT INTO `sys_role_permission` VALUES (17, 136);
INSERT INTO `sys_role_permission` VALUES (20, 2);
INSERT INTO `sys_role_permission` VALUES (20, 3);
INSERT INTO `sys_role_permission` VALUES (20, 4);
INSERT INTO `sys_role_permission` VALUES (20, 6);
INSERT INTO `sys_role_permission` VALUES (20, 7);
INSERT INTO `sys_role_permission` VALUES (20, 8);
INSERT INTO `sys_role_permission` VALUES (20, 9);
INSERT INTO `sys_role_permission` VALUES (20, 13);

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1);
INSERT INTO `sys_role_user` VALUES (2, 2);
INSERT INTO `sys_role_user` VALUES (4, 3);
INSERT INTO `sys_role_user` VALUES (4, 27);
INSERT INTO `sys_role_user` VALUES (17, 22);
INSERT INTO `sys_role_user` VALUES (17, 24);
INSERT INTO `sys_role_user` VALUES (20, 26);

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'system', '超级管理员', '$2a$10$LFhNJ2KEthi5SDCcLKv95.rWbo2c/91y7sriFNixC2wZGThsPFPBC', 1, 1, '2022-01-28 23:06:55', 1, '武汉', '超级管理员', '18812345678', '18812345678@qq.com');
INSERT INTO `sys_user` VALUES (2, 'ls', '李四', '$2a$10$zuFLW1qifdQA4qniH4aXS.lK3Llkelo5gYCoh0FjxwHweRIFPyfGm', 1, 1, '2022-01-28 23:06:55', 2, '陕西', '老四啊111', '18812345678', '18812345678@qq.com');
INSERT INTO `sys_user` VALUES (3, 'ww', '王五', '$2a$10$4BoBgl5cjNDqBgeFhtG/d.qJ3h5tEYDIievY4ij7LU3Gy7MTmGmnC', 1, 1, '2022-01-28 23:06:55', 3, '深圳', '王五', '18812345678', '18812345678@qq.com');
INSERT INTO `sys_user` VALUES (22, 'zs', '张三', '$2a$10$bom2D5ofiFBQSee/1EQPM.SroUm3DGKpnhGKvHMvypgHwao4H8ty2', 1, 1, '2024-03-25 20:19:15', 2, '北京', '测试账号', '18812345678', '18812345678@123.com');
INSERT INTO `sys_user` VALUES (24, 'cs', 'ceshi', '$2a$10$WUY/KVbA5GtZtBv.gdudCOBq0gK7hYYj6bvY63aHDZ4oz6hKDdNmy', 1, 1, '2024-03-26 13:05:53', 2, '111', '111', '111', '111');
INSERT INTO `sys_user` VALUES (26, 'test', 'test', '$2a$10$RJTc4QbEYSTHO64jA6bRJ.9BNhoIIQZ5xw6TjV.7l2H/Vq.kVmgZi', 1, 1, '2024-03-26 16:11:01', 1, '1', '1', '1', '1');
INSERT INTO `sys_user` VALUES (27, 'ren', '任轩辰', '$2a$10$vcdhMyL5hnidJph12cBJkeDr9ObWNTDYMMOe2ycncmkHLkWw/hNUW', 1, 1, '2024-10-16 13:48:02', 6, '电子校', '89cd8d', '13930867897', '1030815337@qq.com');

-- AI 相关表（新增）
-- ----------------------------
-- Table structure for ai_resume_analysis
-- ----------------------------
DROP TABLE IF EXISTS `ai_resume_analysis`;
CREATE TABLE `ai_resume_analysis` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
`candidate_name` varchar(100) NULL DEFAULT NULL COMMENT '候选人姓名',
`resume_text` text NULL COMMENT '简历文本',
`job_requirement` text NULL COMMENT '岗位要求',
`analysis_result` text NULL COMMENT '分析结果',
`match_score` decimal(5,2) NULL DEFAULT NULL COMMENT '匹配分数',
`status` int NULL DEFAULT 0 COMMENT '状态',
`analyst_id` bigint NULL DEFAULT NULL COMMENT '分析师ID',
`remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
`deleted` int NULL DEFAULT 0 COMMENT '删除标记',
`create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ai_interview_session
-- ----------------------------
DROP TABLE IF EXISTS `ai_interview_session`;
CREATE TABLE `ai_interview_session` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
`session_id` varchar(64) NULL DEFAULT NULL COMMENT '会话ID',
`candidate_id` bigint NULL DEFAULT NULL COMMENT '候选人ID',
`candidate_name` varchar(50) NULL DEFAULT NULL COMMENT '候选人姓名',
`position` varchar(100) NULL DEFAULT NULL COMMENT '应聘岗位',
`current_state` varchar(50) NULL DEFAULT NULL COMMENT '当前状态',
`questions` text NULL COMMENT '问题列表',
`answers` text NULL COMMENT '回答列表',
`evaluation` text NULL COMMENT '评估结果',
`final_decision` varchar(50) NULL DEFAULT NULL COMMENT '最终决策',
`score` decimal(5,2) NULL DEFAULT NULL COMMENT '评分',
`status` int NULL DEFAULT 0 COMMENT '状态',
`deleted` int NULL DEFAULT 0 COMMENT '删除标记',
`create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`) USING BTREE,
UNIQUE INDEX `session_id`(`session_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ai_knowledge_doc
-- ----------------------------
DROP TABLE IF EXISTS `ai_knowledge_doc`;
CREATE TABLE `ai_knowledge_doc` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
`title` varchar(200) NULL DEFAULT NULL COMMENT '文档标题',
`content` text NULL COMMENT '文档内容',
`category` varchar(50) NULL DEFAULT NULL COMMENT '分类',
`tags` varchar(200) NULL DEFAULT NULL COMMENT '标签',
`vector_id` varchar(100) NULL DEFAULT NULL COMMENT '向量ID',
`status` int NULL DEFAULT 0 COMMENT '状态',
`deleted` int NULL DEFAULT 0 COMMENT '删除标记',
`create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
-- ==================== AI 相关表（对话记忆 + 知识库）====================

-- AI 对话会话表（对话上下文记忆）
DROP TABLE IF EXISTS `chat_session`;
CREATE TABLE `chat_session` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `session_id` VARCHAR(64) NOT NULL COMMENT '会话ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `user_message` TEXT COMMENT '用户消息',
  `ai_message` TEXT COMMENT 'AI回复',
  `session_type` VARCHAR(50) DEFAULT 'hr_chat' COMMENT '会话类型（hr_chat|interview|resume）',
  `role` VARCHAR(20) NOT NULL COMMENT '角色（user|assistant）',
  `message_time` BIGINT DEFAULT NULL COMMENT '消息时间戳',
  `deleted` INT DEFAULT 0 COMMENT '删除标记',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_session_id`(`session_id`),
  INDEX `idx_user_id`(`user_id`),
  INDEX `idx_session_type`(`session_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI对话会话表';

-- AI 知识库文档表（用于RAG）
DROP TABLE IF EXISTS `ai_knowledge_doc`;
CREATE TABLE `ai_knowledge_doc` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` VARCHAR(200) NOT NULL COMMENT '文档标题',
  `content` TEXT NOT NULL COMMENT '文档内容',
  `category` VARCHAR(50) DEFAULT 'policy' COMMENT '分类（policy|faq|training）',
  `tags` VARCHAR(200) DEFAULT NULL COMMENT '标签（逗号分隔）',
  `vector_id` VARCHAR(100) DEFAULT NULL COMMENT 'Pinecone向量ID',
  `status` INT DEFAULT 0 COMMENT '状态（0:待处理,1:已向量化,2:失败）',
  `deleted` INT DEFAULT 0 COMMENT '删除标记',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_category`(`category`),
  INDEX `idx_status`(`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI知识库文档表';

-- AI 简历分析记录表
DROP TABLE IF EXISTS `ai_resume_analysis`;
CREATE TABLE `ai_resume_analysis` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `candidate_name` VARCHAR(100) DEFAULT NULL COMMENT '候选人姓名',
  `resume_text` TEXT NOT NULL COMMENT '简历文本',
  `job_requirement` TEXT DEFAULT NULL COMMENT '岗位要求',
  `analysis_result` TEXT COMMENT '分析结果JSON',
  `match_score` DECIMAL(5,2) DEFAULT NULL COMMENT '匹配分数',
  `status` INT DEFAULT 0 COMMENT '状态',
  `analyst_id` BIGINT DEFAULT NULL COMMENT '分析师ID',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `deleted` INT DEFAULT 0 COMMENT '删除标记',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_candidate_name`(`candidate_name`),
  INDEX `idx_match_score`(`match_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI简历分析记录表';
