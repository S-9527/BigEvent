# 构建阶段
FROM eclipse-temurin:25-jdk-alpine AS build

WORKDIR /app

# 复制 Gradle 包装器
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# 修复执行权限
RUN chmod +x gradlew

# 下载依赖（利用 Docker 缓存）
RUN ./gradlew dependencies --no-daemon

# 复制源码并构建 JAR
COPY src src
RUN ./gradlew bootJar --no-daemon -x test

# 运行阶段
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]