package com.taskflow.userservice.service;

import com.taskflow.userservice.domain.User;
import com.taskflow.userservice.dto.*;
import com.taskflow.userservice.exception.EmailAlreadyUsedException;
import com.taskflow.userservice.exception.InvalidCredentialsException;
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
    private final TokenService tokenService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
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

    public LoginReponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String token = tokenService.generateToken(user);
        return new LoginReponse(token, "Bearer");
    }

    public UserCredentialsResponse findCredentialsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(0L));
        return new UserCredentialsResponse(
                user.getId(), user.getName(), user.getEmail(),  user.getPasswordHash()
        );
    }
}
