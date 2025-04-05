package com.allyoucanexercise.back_end.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allyoucanexercise.back_end.ExerciseApplication;
import com.allyoucanexercise.back_end.exercise.Exercise;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(final UserService userService, final UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // log.info("in register on controller. username is", username);
        try {
            userService.registerUser(user.getUsername(), user.getPassword());
            return ResponseEntity.ok("User registered successfully!");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpServletRequest request) {
        boolean authenticated = userService.authenticateUser(user.getUsername(), user.getPassword());

        if (authenticated) {
            userService.setSession(user.getUsername(), request);
            return ResponseEntity.ok(user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy session
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/auth/checkusersession")
    public String checkUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        log.debug("session is", session);
        User user = null;
        if (session != null && session.getAttribute("username") != null) {
            log.debug("inside checkuser, session is not null, username is not null");
            user = userRepository.findByUsername(session.getAttribute("username").toString()).orElse(null);
        }
        return user.getUsername();
    }

    @GetMapping("/api/users")
    List<User> findAll() {
        return userRepository.findAll();
    }

}
