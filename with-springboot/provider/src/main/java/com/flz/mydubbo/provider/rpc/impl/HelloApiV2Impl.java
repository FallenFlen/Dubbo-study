package com.flz.mydubbo.provider.rpc.impl;

import com.flz.mydubbo.api.HelloApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(group = "HelloApi", version = "v2") // 该注解可以基于sb去发布dubbo服务
public class HelloApiV2Impl implements HelloApi {
    @Override
    public String getHelloInfo(String message) {
        return "Hello DubboV2," + message;
    }
}
