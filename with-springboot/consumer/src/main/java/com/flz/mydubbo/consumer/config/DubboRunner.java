package com.flz.mydubbo.consumer.config;

import com.flz.mydubbo.api.HelloApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DubboRunner implements CommandLineRunner {
    @DubboReference
    private HelloApi helloApi;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    String response = helloApi.getHelloInfo("respect");
                    log.info("Receive rpc result:{}", response);
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
