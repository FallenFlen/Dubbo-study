package com.flz.mydubbo.businessservice.controller;

import com.flz.mydubbo.businessservice.dto.request.UserLoginRequestDTO;
import com.flz.mydubbo.businessservice.service.UserService;
import com.flz.mydubbo.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody UserLoginRequestDTO requestDTO) throws InterruptedException {
        return userService.login(requestDTO);
    }
}

