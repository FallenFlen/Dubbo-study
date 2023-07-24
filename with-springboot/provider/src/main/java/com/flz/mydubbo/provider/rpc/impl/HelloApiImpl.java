package com.flz.mydubbo.provider.rpc.impl;

import com.flz.mydubbo.api.HelloApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService // 该注解可以基于sb去发布dubbo服务
public class HelloApiImpl implements HelloApi {
    @Override
    public String getHelloInfo(String message) {
        return "Hello Dubbo," + message;
    }
}
