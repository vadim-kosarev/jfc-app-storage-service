plugins {
    java
    id("org.springframework.boot") version "3.2.4"
//    id("org.springframework.boot") version "2.6.15"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "dev.vk.jfc.app.storage"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

springBoot {
    buildInfo()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.minio:minio:8.5.9")
    implementation("org.javatuples:javatuples:1.2")
    implementation("org.aspectj:aspectjrt")
    implementation("com.jcabi:jcabi-aspects:0.25.1")
    implementation("org.modelmapper:modelmapper:3.2.0")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("org.apache.commons:commons-text:1.12.0")

    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("javax.servlet:servlet-api:2.5")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation(project(":modules:jfc-common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

