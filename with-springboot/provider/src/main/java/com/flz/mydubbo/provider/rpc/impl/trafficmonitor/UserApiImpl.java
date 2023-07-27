package com.flz.mydubbo.provider.rpc.impl.trafficmonitor;

import com.flz.mydubbo.api.trafficmonitor.UserApi;
import com.flz.mydubbo.common.utils.RandomUtils;
import com.flz.mydubbo.dto.UserResponseDTO;
import org.apache.dubbo.config.annotation.DubboService;

import java.time.LocalDateTime;

// 默认1s超时
@DubboService(group = "UserApi", version = "v1")
public class UserApiImpl implements UserApi {
    @Override
    public UserResponseDTO login(String username, String password) throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(1500);
        return UserResponseDTO.builder()
                .username(username)
                .id(RandomUtils.uuid32())
                .lastLoginTime(LocalDateTime.now())
                .build();
    }
}
