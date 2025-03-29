package com.allyoucanexercise.back_end.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    void registerUser(@Valid @RequestBody String username, String password) {
        try {
            userService.registerUser(username, password);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
            // do I need to return the error to the frontend?
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    void login(@Valid @RequestBody String username, String password) {
        userService.authenticateUser(username, password);
    }

}
