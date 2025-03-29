package com.allyoucanexercise.back_end.user;

public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
