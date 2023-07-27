package com.flz.mydubbo.provider.rpc.impl.trafficmonitor;

import com.flz.mydubbo.api.trafficmonitor.UserApi;
import com.flz.mydubbo.common.constant.Constants;
import com.flz.mydubbo.common.utils.RandomUtils;
import com.flz.mydubbo.dto.UserResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.MDC;

import java.time.LocalDateTime;

@Slf4j
@DubboService(group = "UserApi", version = "v1")
public class UserApiImpl implements UserApi {
    @Override
    public UserResponseDTO login(String username, String password) throws InterruptedException {
        log.info("Trace id in UserApiImpl:{}", MDC.get(Constants.TRACE_ID));
        return UserResponseDTO.builder()
                .username(username)
                .id(RandomUtils.uuid32())
                .lastLoginTime(LocalDateTime.now())
                .build();
    }
}
