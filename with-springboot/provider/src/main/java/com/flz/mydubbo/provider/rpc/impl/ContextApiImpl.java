package com.flz.mydubbo.provider.rpc.impl;

import com.flz.mydubbo.api.ContextApi;
import com.flz.mydubbo.api.Provider2HelloApi;
import com.flz.mydubbo.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Map;

@Slf4j
@DubboService(group = "ContextApi", version = "v1")
public class ContextApiImpl implements ContextApi {
    @DubboReference(group = "Provider2HelloApi", version = "v1")
    private Provider2HelloApi provider2HelloApi;

    @Override
    public String transferParam(String param) {
        // 获取客户端的传参
        Map<String, Object> objectAttachments = RpcContext.getServerAttachment().getObjectAttachments();
        log.info("客户端的传参:{}", JsonUtils.silentConvertToStr(objectAttachments));
        log.info("Provider current time is:{}", RpcContext.getServiceContext().isProviderSide());

        // 调用provider2
        String response = provider2HelloApi.hello2("provider1");
        log.info("Provider2 response:{}", response);
        log.info("Provider current time after call provider2 is:{}", RpcContext.getServiceContext().isProviderSide());

        // 回传给客户端
        RpcContext.getServerContext().setAttachment("serverParam", "respect");
        return "transferParam:" + param;
    }
}
