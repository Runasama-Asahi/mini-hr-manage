package com.renxuanchen.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3.0 配置类
 * 访问地址：http://localhost:8080/swagger-ui.html
 * API Docs：http://localhost:8080/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("HR 管理系统 API")
                        .description("""
                                ## HR Management System REST API

                                ### 功能模块
                                - **用户管理**：员工信息、密码重置
                                - **部门管理**：组织架构管理
                                - **角色权限**：RBAC 权限控制
                                - **考勤管理**：打卡记录、迟到早退统计
                                - **薪资管理**：工资核算、扣款明细
                                - **绩效管理**：业绩提成、目标完成情况
                                - **培训管理**：培训计划、参与者管理
                                - **转正申请**：试用期员工转正审批
                                - **AI 助手**：智能问答、简历分析、面试辅助

                                ### 认证方式
                                JWT Bearer Token 认证，在请求头中添加：
                                ```
                                Authorization: Bearer <token>
                                ```
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("HR Team")
                                .email("hr-support@company.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("开发环境"),
                        new Server()
                                .url("https://api.hr.company.com")
                                .description("生产环境")))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("请输入 JWT Token")));
    }
}