-- H2 数据库初始化脚本（测试用）
-- 与 MySQL hr.sql 保持一致

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loginname VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    sex INT,
    available INT,
    hiredate TIMESTAMP,
    deptid INT,
    address VARCHAR(255),
    remark VARCHAR(255),
    telephone VARCHAR(255),
    email VARCHAR(255)
);

-- 部门表
CREATE TABLE IF NOT EXISTS sys_dept (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pid INT,
    title VARCHAR(255),
    remark VARCHAR(255),
    address VARCHAR(255),
    available INT,
    create_time TIMESTAMP
);

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    remark VARCHAR(255),
    available INT,
    create_time TIMESTAMP
);

-- 权限/菜单表
CREATE TABLE IF NOT EXISTS sys_permission (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pid INT,
    title VARCHAR(255),
    icon VARCHAR(255),
    href VARCHAR(255),
    open INT,
    available INT,
    type VARCHAR(200)
);

-- 用户-角色关联表
CREATE TABLE IF NOT EXISTS sys_role_user (
    uid BIGINT NOT NULL,
    rid INT NOT NULL,
    PRIMARY KEY (uid, rid)
);

-- 角色-权限关联表
CREATE TABLE IF NOT EXISTS sys_role_permission (
    rid INT NOT NULL,
    pid INT NOT NULL,
    PRIMARY KEY (pid, rid)
);

-- 考勤记录表
CREATE TABLE IF NOT EXISTS b_work_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uid BIGINT,
    work_date VARCHAR(255),
    up_time VARCHAR(255),
    down_time VARCHAR(255),
    status INT,
    create_time TIMESTAMP,
    update_time TIMESTAMP
);

-- 工作日志表（简化）
CREATE TABLE IF NOT EXISTS b_workdaily (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uid BIGINT,
    work_date DATE,
    work_content TEXT,
    create_time TIMESTAMP
);

-- 薪资记录表
CREATE TABLE IF NOT EXISTS b_salary_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uid BIGINT,
    salary_month VARCHAR(255),
    must_salary FLOAT,
    reality_salary FLOAT,
    late_amount FLOAT,
    tax_amount FLOAT,
    merits_amount FLOAT,
    pension_amount FLOAT,
    leave_amount FLOAT,
    create_time TIMESTAMP
);

-- 绩效/提成表
CREATE TABLE IF NOT EXISTS b_merit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    month_quota INT,
    uid BIGINT,
    is_completed INT DEFAULT 0,
    month VARCHAR(7),
    current_amount INT DEFAULT 0
);

-- 培训计划表
CREATE TABLE IF NOT EXISTS b_train_plan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content VARCHAR(255),
    participant VARCHAR(255),
    train_date TIMESTAMP
);

-- 转正申请表
CREATE TABLE IF NOT EXISTS b_convert_apply (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    apply_user_id BIGINT,
    apply_date VARCHAR(255),
    apply_content VARCHAR(255),
    status INT,
    approval_date VARCHAR(255),
    approval_user_id INT,
    approval_opinion VARCHAR(255),
    create_time VARCHAR(255),
    update_time VARCHAR(255)
);

-- AI 简历分析记录表
CREATE TABLE IF NOT EXISTS ai_resume_analysis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_name VARCHAR(100),
    resume_text TEXT,
    job_requirement TEXT,
    analysis_result TEXT,
    match_score DECIMAL(5,2),
    status INT DEFAULT 0,
    analyst_id BIGINT,
    remark VARCHAR(500),
    deleted INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- AI 面试记录表
CREATE TABLE IF NOT EXISTS ai_interview_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(64),
    candidate_id BIGINT,
    candidate_name VARCHAR(50),
    position VARCHAR(100),
    current_state VARCHAR(50),
    questions TEXT,
    answers TEXT,
    evaluation TEXT,
    final_decision VARCHAR(50),
    score DECIMAL(5,2),
    status INT DEFAULT 0,
    deleted INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- AI 知识库文档表
CREATE TABLE IF NOT EXISTS ai_knowledge_doc (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    content TEXT,
    category VARCHAR(50),
    tags VARCHAR(200),
    vector_id VARCHAR(100),
    status INT DEFAULT 0,
    deleted INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);