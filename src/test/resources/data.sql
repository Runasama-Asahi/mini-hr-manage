-- 测试初始数据

-- 插入测试部门
INSERT INTO sys_dept (id, pid, title, remark, address, available, create_time) VALUES
(1, 0, '总经办', '大BOSS', '重庆', 1, CURRENT_TIMESTAMP),
(2, 1, '生产部', '程序员', '武汉', 1, CURRENT_TIMESTAMP),
(3, 1, '销售部', '无', '武汉', 1, CURRENT_TIMESTAMP),
(4, 1, '财务部', '无', '武汉', 1, CURRENT_TIMESTAMP),
(5, 1, '人事部', '人事部', '武汉', 1, CURRENT_TIMESTAMP);

-- 插入测试角色
INSERT INTO sys_role (id, name, remark, available, create_time) VALUES
(1, '超级管理员', '拥有所有菜单权限', 1, CURRENT_TIMESTAMP),
(2, '财务专员', '财务', 1, CURRENT_TIMESTAMP),
(3, '人事专员', '人事', 1, CURRENT_TIMESTAMP),
(4, '普通成员', '普通', 1, CURRENT_TIMESTAMP);

-- 插入测试用户（密码：123456，BCrypt加密）
-- $2a$10$... 对应密码 "123456"
INSERT INTO sys_user (id, loginname, name, password, sex, available, hiredate, deptid, address, remark, telephone, email) VALUES
(1, 'system', '超级管理员', '$2a$10$LFhNJ2KEthi5SDCcLKv95.rWbo2c/91y7sriFNixC2wZGThsPFPBC', 1, 1, CURRENT_TIMESTAMP, 1, '武汉', '超级管理员', '18812345678', '18812345678@qq.com'),
(2, 'ls', '李四', '$2a$10$zuFLW1qifdQA4qniH4aXS.lK3Llkelo5gYCoh0FjxwHweRIFPyfGm', 1, 1, CURRENT_TIMESTAMP, 2, '陕西', '老四', '18812345678', '18812345678@qq.com'),
(3, 'ww', '王五', '$2a$10$4BoBgl5cjNDqBgeFhtG/d.qJ3h5tEYDIievY4ij7LU3Gy7MTmGmnC', 1, 1, CURRENT_TIMESTAMP, 3, '深圳', '王五', '18812345678', '18812345678@qq.com');

-- 用户-角色关联
INSERT INTO sys_role_user (uid, rid) VALUES
(1, 1), -- system -> 超级管理员
(2, 2), -- ls -> 财务专员
(3, 4); -- ww -> 普通成员

-- 插入测试权限
INSERT INTO sys_permission (id, pid, title, icon, href, open, available, type) VALUES
(2, 0, '绩效管理', '&#xe857;', '', 0, 1, 'menu'),
(3, 0, '工资管理', '&#xe65e;', '', 0, 1, 'menu'),
(4, 0, '考勤管理', '&#xe611;', '', 0, 1, 'menu'),
(5, 0, '培训管理', '&#xe628;', '', 0, 1, 'menu'),
(6, 0, '系统管理', '&#xe614;', '', 0, 1, 'menu'),
(7, 2, '考核设置', '&#xe62c;', '/meritManager', 0, 1, 'menu'),
(8, 3, '工资管理', '&#xe658;', '/salaryRecordManager', 0, 1, 'menu'),
(9, 4, '出勤记录', '&#xe637;', '/workRecordManager', 0, 1, 'menu'),
(11, 5, '培训计划', '&#xe756;', '/trainPlanManager', 0, 1, 'menu'),
(12, 6, '员工管理', '&#xe770;', '/userManager', 0, 1, 'menu'),
(13, 6, '部门管理', '&#xe770;', '/deptManager', 0, 1, 'menu'),
(14, 6, '角色管理', '&#xe770;', '/roleManager', 0, 1, 'menu'),
(118, 5, '申请审批', '&#xe756;', '/convertApplyManager', 0, 1, 'menu'),
(135, 5, '申请', '&#xe756;', '/convertApplyUser', 0, 1, 'menu'),
(136, 3, '工资查询', '&#xe658;', '/salaryRecordUser', 0, 1, 'menu');

-- 角色-权限关联
INSERT INTO sys_role_permission (rid, pid) VALUES
(1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 11), (1, 12), (1, 13), (1, 14), (1, 118),
(2, 3), (2, 8), (2, 136),
(3, 6), (3, 12), (3, 13),
(4, 136);

-- 插入测试考勤记录
INSERT INTO b_work_record (uid, work_date, up_time, down_time, status, create_time) VALUES
(1, '2026-04-25', '09:00:00', '18:00:00', 1, CURRENT_TIMESTAMP),
(2, '2026-04-25', '08:30:00', '17:30:00', 1, CURRENT_TIMESTAMP),
(3, '2026-04-25', '09:15:00', '18:15:00', 2, CURRENT_TIMESTAMP);

-- 插入测试薪资记录
INSERT INTO b_salary_record (uid, salary_month, must_salary, reality_salary, late_amount, tax_amount, merits_amount, pension_amount, leave_amount, create_time) VALUES
(1, '2026-03', 10000, 10000, 0, 500, 1000, 500, 0, CURRENT_TIMESTAMP),
(2, '2026-03', 8000, 7500, 100, 300, 500, 400, 200, CURRENT_TIMESTAMP);

-- 插入测试绩效记录
INSERT INTO b_merit (month_quota, uid, is_completed, month, current_amount) VALUES
(20, 1, 1, '2026-03', 22),
(15, 2, 1, '2026-03', 15),
(10, 3, 0, '2026-03', 8);

-- 插入测试培训记录
INSERT INTO b_train_plan (title, content, participant, train_date) VALUES
('新员工入职培训', '企业文化与制度', '全体新员工', CURRENT_TIMESTAMP);