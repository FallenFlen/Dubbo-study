package com.flz.mydubbo.provider.rpc.impl;

import com.flz.mydubbo.api.AsyncApi;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@DubboService
public class AsyncApiImpl implements AsyncApi {
    @Override
    public CompletableFuture<String> fetchAsyncInfo(String msg) {
        return CompletableFuture.supplyAsync(() -> String.format("Hello async:%s", msg));
    }

    @Override
    public String fetchAsyncInfoWithAsyncContext(String msg) {
        AsyncContext asyncContext = RpcContext.startAsync();
        CompletableFuture.runAsync(() -> {
            asyncContext.signalContextSwitch();
            try {
                TimeUnit.MILLISECONDS.sleep(500L);
                asyncContext.write("fetchAsyncInfoWithAsyncContext:" + msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
                asyncContext.write("fetchAsyncInfoWithAsyncContext failed:" + e.getMessage());
            }
        });
        return null;
    }
}
