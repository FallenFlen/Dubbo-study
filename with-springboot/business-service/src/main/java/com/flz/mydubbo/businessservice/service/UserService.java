package com.flz.mydubbo.businessservice.service;

import com.flz.mydubbo.api.trafficmonitor.UserApi;
import com.flz.mydubbo.businessservice.dto.request.UserLoginRequestDTO;
import com.flz.mydubbo.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @DubboReference(group = "UserApi", version = "v1")
    private UserApi userApi;

    public UserResponseDTO login(UserLoginRequestDTO requestDTO) throws InterruptedException {
        return userApi.login(requestDTO.getUsername(), requestDTO.getPassword());
    }
}
