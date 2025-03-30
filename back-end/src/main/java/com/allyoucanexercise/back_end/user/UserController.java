package com.allyoucanexercise.back_end.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(final UserService userService, final UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user.getUsername(), user.getPassword());
            return ResponseEntity.ok("User registered successfully!");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpServletRequest request) {
        boolean authenticated = userService.authenticateUser(user.getUsername(), user.getPassword());

        if (authenticated) {
            userService.setSession(user.getUsername(), request);
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy session
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/checkusersession")
    public User checkUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null && session.getAttribute("username") != null) {
            user = userRepository.findByUsername(session.getAttribute("username").toString()).orElse(null);
        }
        return user;
    }

}
