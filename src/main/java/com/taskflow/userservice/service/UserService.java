package com.taskflow.userservice.service;

import com.taskflow.userservice.domain.User;
import com.taskflow.userservice.dto.CreateUserRequest;
import com.taskflow.userservice.dto.UserResponse;
import com.taskflow.userservice.exception.EmailAlreadyUsedException;
import com.taskflow.userservice.exception.UserNotFoundException;
import com.taskflow.userservice.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

    public Page<UserResponse> list(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail()));
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
