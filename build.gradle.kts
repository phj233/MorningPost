plugins {
    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("net.mamoe.mirai-console") version "2.15.0"
}

group = "top.phj233"
version = "0.1.0"


repositories {
    maven("https://maven.aliyun.com/repository/public")
    maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
    mavenCentral()
}

dependencies{
    implementation("cn.hutool:hutool-all:5.8.22")
}

mirai {
    jvmTarget = JavaVersion.VERSION_17
}
