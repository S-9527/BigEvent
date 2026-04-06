plugins {
    java
    id("org.springframework.boot") version "4.0.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "demo"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:4.0.1")
    implementation("com.github.pagehelper:pagehelper-spring-boot-starter:2.1.1")
    implementation("com.auth0:java-jwt:4.5.1")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("com.aliyun.oss:aliyun-sdk-oss:3.18.5")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:4.0.1")
    runtimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
