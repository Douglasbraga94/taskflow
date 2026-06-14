package com.taskflow.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min=8, message = "A senha deve ter no mínimo 8 caracteres")
        String password) {

}
