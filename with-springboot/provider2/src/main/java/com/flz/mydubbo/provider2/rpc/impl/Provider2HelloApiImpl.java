package com.flz.mydubbo.provider2.rpc.impl;

import com.flz.mydubbo.api.Provider2HelloApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(group = "Provider2HelloApi", version = "v1")
public class Provider2HelloApiImpl implements Provider2HelloApi {
    @Override
    public String hello2(String msg) {
        return "Hello provider2 " + msg;
    }
}
