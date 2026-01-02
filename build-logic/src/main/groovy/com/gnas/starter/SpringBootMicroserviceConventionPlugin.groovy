package com.gnas.starter

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.JavaVersion
import org.gradle.api.tasks.compile.JavaCompile

class SpringBootMicroserviceConventionPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // Apply common plugins
        project.pluginManager.apply 'java'
        project.pluginManager.apply 'org.springframework.boot'
        project.pluginManager.apply 'io.spring.dependency-management'

        // Common coordinates
        project.group = 'com.gnas.starter'
        project.version = '1.0.0-SNAPSHOT'

        // Java version, encoding, etc.
        project.tasks.withType(JavaCompile).configureEach { JavaCompile task ->
            task.sourceCompatibility = JavaVersion.VERSION_17.toString()
            task.targetCompatibility = JavaVersion.VERSION_17.toString()
            task.options.encoding = 'UTF-8'
        }

        // Repositories for all modules
        project.repositories {
            mavenLocal()
            mavenCentral()
        }

        // Common dependencies
        project.dependencies {
            implementation 'org.springframework.boot:spring-boot-starter-web'
            implementation 'org.springframework.boot:spring-boot-starter-actuator'
            implementation 'org.springframework.boot:spring-boot-starter-aop'
            implementation 'com.fasterxml.jackson.core:jackson-databind:2.20.0'

            compileOnly 'org.projectlombok:lombok'
            annotationProcessor 'org.projectlombok:lombok'
            developmentOnly 'org.springframework.boot:spring-boot-devtools'

            testImplementation 'org.springframework.boot:spring-boot-starter-test'
            testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
        }
    }
}
