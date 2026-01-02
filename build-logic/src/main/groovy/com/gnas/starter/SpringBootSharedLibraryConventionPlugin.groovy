package com.gnas.starter

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.JavaVersion
import org.gradle.api.tasks.compile.JavaCompile

class SpringBootSharedLibraryConventionPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // Library-style module, no Boot app
        project.pluginManager.apply 'java-library'
        project.pluginManager.apply 'io.spring.dependency-management'

        project.group = 'com.gnas.starter'
        project.version = '1.0.0-SNAPSHOT'

        project.tasks.withType(JavaCompile).configureEach { JavaCompile t ->
            t.sourceCompatibility = JavaVersion.VERSION_17.toString()
            t.targetCompatibility = JavaVersion.VERSION_17.toString()
            t.options.encoding = 'UTF-8'
        }

        project.repositories {
            mavenCentral()
//            maven {
//                url 'https://repo.my-company.com/maven2'
//            }
        }

        // Optionally: align versions with Spring Boot BOM
        project.dependencies {
            api platform("org.springframework.boot:spring-boot-dependencies:3.3.4")

            api('org.springframework.boot:spring-boot-starter')

            testImplementation('org.springframework.boot:spring-boot-starter-test')
        }
    }
}
