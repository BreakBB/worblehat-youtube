plugins {
    application // TODO replace with spring boot plugin?
}

repositories {
  mavenCentral()
}

dependencies {
    implementation(project(":worblehat-domain"))
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.1.4.RELEASE"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework:spring-tx")
    implementation("javax.persistence:javax.persistence-api")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    // postgresql
    // lombok
    implementation("org.apache.commons:commons-lang3")
    implementation("commons-validator:commons-validator:1.6")
    // validation-api
    compileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.14.1"))
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.google.guava:guava:29.0-jre") // TODO, replace with Set.of
}

tasks {
  test {
    useJUnitPlatform()
  }
  processResources {
    filesMatching("*.properties") {
      expand(project.properties)
    }
  }
}

application {
  mainClass.set("de.codecentric.psd.Worblehat")
}
