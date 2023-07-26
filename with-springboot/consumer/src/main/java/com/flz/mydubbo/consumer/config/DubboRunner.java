package com.flz.mydubbo.consumer.config;

import com.flz.mydubbo.api.AsyncApi;
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
    @DubboReference
    private AsyncApi asyncApi;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    String response = helloApi.getHelloInfo("respect");
                    log.info("Receive hello rpc result:{}", response);
                    asyncApi.fetchAsyncInfo("async").thenAcceptAsync(s -> log.info("Receive async hello rpc result:{}", s));
                    String asyncContextResult = asyncApi.fetchAsyncInfoWithAsyncContext("async context");
                    System.out.println(asyncContextResult);
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
