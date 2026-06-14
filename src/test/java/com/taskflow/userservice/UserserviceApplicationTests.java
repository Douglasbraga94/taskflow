package com.taskflow.userservice.service;

import com.taskflow.userservice.domain.User;
import com.taskflow.userservice.dto.CreateUserRequest;
import com.taskflow.userservice.dto.UserResponse;
import com.taskflow.userservice.exception.EmailAlreadyUsedException;
import com.taskflow.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void deveRegistrarUsuarioComSucesso() {
        // ARRANGE (preparar): definimos o comportamento dos dublês
        CreateUserRequest request =
                new CreateUserRequest("Ana", "ana@taskflow.com", "senha12345");

        when(userRepository.existsByEmail("ana@taskflow.com")).thenReturn(false);
        when(passwordEncoder.encode("senha12345")).thenReturn("HASH_FAKE");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // ACT (agir): executamos o método sob teste
        UserResponse response = userService.register(request);

        // ASSERT (verificar): conferimos o resultado
        assertThat(response.name()).isEqualTo("Ana");
        assertThat(response.email()).isEqualTo("ana@taskflow.com");
        verify(passwordEncoder).encode("senha12345");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // ARRANGE: o dublê finge que o e-mail já existe
        CreateUserRequest request =
                new CreateUserRequest("Ana", "ana@taskflow.com", "senha12345");
        when(userRepository.existsByEmail("ana@taskflow.com")).thenReturn(true);

        // ACT + ASSERT: esperamos que lance a exceção, e que NÃO salve
        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(EmailAlreadyUsedException.class);

        verify(userRepository, never()).save(any());
    }
}