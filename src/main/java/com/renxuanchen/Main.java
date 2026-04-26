package com.renxuanchen;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/hr?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("admin")
                            .outputDir(projectPath + "/src/main/java")
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.TIME_PACK)
                            .enableSwagger();
                })
                .packageConfig(builder -> {
                    builder.parent("com.renxuanchen")
                            .moduleName(null)
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("b_convert_apply", "b_merit", "b_salary_record", "b_train_plan", 
                                    "b_work_record", "b_workdaily", "sys_dept", "sys_permission", 
                                    "sys_role", "sys_role_permission", "sys_role_user", "sys_user")
                            .addTablePrefix("b_", "sys_")
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .naming(com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.underline_to_camel)
                            .columnNaming(com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.underline_to_camel)
                            .logicDeleteColumnName("deleted")
                            .logicDeletePropertyName("deleted")
                            .controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }
}
