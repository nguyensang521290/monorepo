
package com.gnas.starter.outbox.config;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@AutoConfiguration
@ComponentScan(basePackages = "com.gnas.starter.outbox.service")
@EntityScan(basePackages = "com.gnas.starter.outbox.domain")
@EnableJpaRepositories(basePackages = "com.gnas.starter.outbox.repository")
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "15m")
@Slf4j
public class OutboxAutoConfiguration {

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        log.info("Configuring ShedLock with JdbcTemplateLockProvider");
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .usingDbTime()
                        .build()
        );
    }
}
