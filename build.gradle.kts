plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "dev.vk.jfc.app.storage"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("io.minio:minio:8.5.9")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.javatuples:javatuples:1.2")
    implementation("org.aspectj:aspectjrt")
    implementation("com.jcabi:jcabi-aspects:0.25.1")
    implementation("commons-beanutils:commons-beanutils:1.9.4")

    implementation(project(":modules:jfc-common"))

    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
