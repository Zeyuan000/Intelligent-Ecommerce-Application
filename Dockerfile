# 使用 maven:3.6.3-jdk-8-slim 作为基础镜像，包含Maven和Java 8
FROM maven:3.6.3-jdk-8-slim AS build
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
