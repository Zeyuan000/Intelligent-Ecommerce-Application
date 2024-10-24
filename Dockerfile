# 第一个阶段：用于构建 Maven 项目
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# 复制项目的 Maven 配置文件和代码到工作目录中
COPY pom.xml .
RUN mvn -B dependency:resolve dependency:resolve-plugins

COPY src ./src
RUN mvn package -DskipTests

# 使用一个轻量级的 JDK 运行应用程序
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

