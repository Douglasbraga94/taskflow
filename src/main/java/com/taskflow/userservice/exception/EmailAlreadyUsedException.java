package com.taskflow.userservice.exception;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException (String email){
        super("E-mail já cadastrado: "+email);
    };
}
