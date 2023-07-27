package com.flz.mydubbo.api.trafficmonitor;

import com.flz.mydubbo.dto.UserResponseDTO;

public interface UserApi {
    UserResponseDTO login(String username, String password) throws InterruptedException;
}
