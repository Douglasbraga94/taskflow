package com.taskflow.userservice.controller;


import com.taskflow.userservice.dto.LoginReponse;
import com.taskflow.userservice.dto.LoginRequest;
import com.taskflow.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginReponse login(@Valid @RequestBody LoginRequest request){
        return userService.login(request);
    }
}
