package com.taskflow.userservice.dto;

public record UserCredentialsResponse(Long id, String name, String email, String passwordHash) {
}
