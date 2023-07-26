package com.flz.mydubbo.consumer.config;

import com.flz.mydubbo.api.AsyncApi;
import com.flz.mydubbo.api.ContextApi;
import com.flz.mydubbo.api.HelloApi;
import com.flz.mydubbo.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.config.ReferenceCache;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.utils.SimpleReferenceCache;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DubboRunner implements CommandLineRunner {
    @DubboReference(group = "HelloApi", version = "v1")
    private HelloApi helloApiV1;
    @DubboReference(group = "HelloApi", version = "v2")
    private HelloApi helloApiV2;
    @DubboReference
    private AsyncApi asyncApi;
    @DubboReference(group = "ContextApi", version = "v1")
    private ContextApi contextApi;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    // hello
//                    callHelloApi();
                    // async
//                    callAsyncApi();
                    // context
//                    callContextApi();
                    // generic
                    callGenericApi();

                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void callGenericApi() {
        GenericService genericService = buildGenericService("com.flz.mydubbo.api.HelloApi", "HelloApi", "v1");
        //传入需要调用的方法，参数类型列表，参数列表
        Object result = genericService.$invoke("getHelloInfo", new String[]{"java.lang.String"}, new Object[]{"g1"});
        log.info("GenericService response:{}", JsonUtils.silentConvertToStr(result));
    }

    private GenericService buildGenericService(String interfaceClass, String group, String version) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface(interfaceClass);
        reference.setVersion(version);
        //开启泛化调用
        reference.setGeneric("true");
        reference.setTimeout(60000);
        reference.setGroup(group);
        ReferenceCache cache = SimpleReferenceCache.getCache();
        try {
            return cache.get(reference);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void callContextApi() {
        // 向服务端传参
        RpcContext.getClientAttachment().setAttachment("clientParam", "respect-client");
        log.info("Client attachment before getting response:{}", JsonUtils.silentConvertToStr(RpcContext.getClientAttachment().getObjectAttachments()));
        String response = contextApi.transferParam("Here is client");
        // 接收服务端的回参
        log.info("ContextApi response:{}", response);
        Map<String, Object> responseParam = RpcContext.getServerContext().getObjectAttachments();
        log.info("ContextApi response param:{}", JsonUtils.silentConvertToStr(responseParam));
        // 在调用后，RpcContext信息被清除
        log.info("Client attachment after getting response:{}", JsonUtils.silentConvertToStr(RpcContext.getClientAttachment().getObjectAttachments()));
    }

    private void callHelloApi() {
        String response = helloApiV1.getHelloInfo("respect");
        log.info("Receive hello(v1) rpc result:{}", response);
        String responseV2 = helloApiV2.getHelloInfo("respect");
        log.info("Receive hello(v2) rpc result:{}", responseV2);
    }

    private void callAsyncApi() {
        asyncApi.fetchAsyncInfo("async").thenAcceptAsync(s -> log.info("Receive async hello rpc result:{}", s));
        String asyncContextResult = asyncApi.fetchAsyncInfoWithAsyncContext("async context");
        log.info(asyncContextResult);
    }
}
