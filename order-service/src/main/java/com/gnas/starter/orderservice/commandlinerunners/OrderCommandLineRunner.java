package com.gnas.starter.orderservice.commandlinerunners;

import com.gnas.starter.sharedlib.helper.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String sample = StringHelper.getSample();
        log.info(sample);
    }
}
