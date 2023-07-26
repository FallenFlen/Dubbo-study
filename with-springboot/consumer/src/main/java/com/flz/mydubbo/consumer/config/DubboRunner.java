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
    @DubboReference(group = "HelloApi",version = "v1")
    private HelloApi helloApiV1;
    @DubboReference(group = "HelloApi",version = "v2")
    private HelloApi helloApiV2;
    @DubboReference
    private AsyncApi asyncApi;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    String response = helloApiV1.getHelloInfo("respect");
                    log.info("Receive hello(v1) rpc result:{}", response);
                    String responseV2 = helloApiV2.getHelloInfo("respect");
                    log.info("Receive hello(v2) rpc result:{}", responseV2);

                    // async
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
