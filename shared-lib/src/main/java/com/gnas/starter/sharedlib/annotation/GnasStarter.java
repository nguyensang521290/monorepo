package com.gnas.starter.sharedlib.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication
public @interface GnasStarter {
    @AliasFor(annotation = SpringBootApplication.class, attribute = "scanBasePackages")
    String[] myScanBasePackages() default {};
}
