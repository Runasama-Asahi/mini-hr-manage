# Multi-stage build for HR Management System
# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# 安装Maven
RUN apk add --no-cache maven

# 复制pom.xml和源代码
COPY pom.xml .
COPY src ./src
COPY .mvn .mvn
COPY mvnw .

# 下载依赖（利用Docker缓存）
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# 构建应用
RUN ./mvnw package -DskipTests -Pprod

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 安装运行时依赖
RUN apk add --no-cache curl

# 从构建阶段复制jar包
COPY --from=builder /app/target/hr.jar app.jar
COPY --from=builder /app/src/main/resources/templates ./templates
COPY --from=builder /app/src/main/resources/static ./static

# 创建日志目录
RUN mkdir -p logs

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 环境变量
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"

# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=prod"]