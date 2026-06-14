package com.taskflow.userservice.service;

import com.taskflow.userservice.domain.User;
import com.taskflow.userservice.dto.CreateUserRequest;
import com.taskflow.userservice.dto.UserResponse;
import com.taskflow.userservice.exception.EmailAlreadyUsedException;
import com.taskflow.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new EmailAlreadyUsedException(request.email());
        }

        String hash = passwordEncoder.encode(request.password());
        User user = new User(request.name(), request.email(), hash);
        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail());
    }
}
