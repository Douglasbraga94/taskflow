package com.taskflow.userservice.controller;

import com.taskflow.userservice.dto.UserCredentialsResponse;
import com.taskflow.userservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController {

    private final UserService userService;

    public InternalUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/by-email")
    public UserCredentialsResponse byEmail(@RequestParam String email) {
        return userService.findCredentialsByEmail(email);
    }
}
