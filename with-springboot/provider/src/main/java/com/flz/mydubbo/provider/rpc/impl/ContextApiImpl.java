package com.flz.mydubbo.provider.rpc.impl;

import com.flz.mydubbo.api.ContextApi;
import com.flz.mydubbo.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Map;

@Slf4j
@DubboService(group = "ContextApi", version = "v1")
public class ContextApiImpl implements ContextApi {
    @Override
    public String transferParam(String param) {
        // 获取客户端的传参
        Map<String, Object> objectAttachments = RpcContext.getServerAttachment().getObjectAttachments();
        log.info("客户端的传参:{}", JsonUtils.silentConvertToStr(objectAttachments));
        // 回传给客户端
        RpcContext.getServerContext().setAttachment("serverParam", "respect");
        return "transferParam:" + param;
    }
}
