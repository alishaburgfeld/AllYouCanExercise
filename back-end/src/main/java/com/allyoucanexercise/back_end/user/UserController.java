package com.allyoucanexercise.back_end.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/api/auth/register")
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
    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpServletRequest request) {
        boolean authenticated = userService.authenticateUser(user.getUsername(), user.getPassword());

        if (authenticated) {
            userService.setSessionAndSecurityContext(user, request);

            Authentication authtest = SecurityContextHolder.getContext().getAuthentication();
            // System.out.println("Authenticated user: " + authtest.getName());
            return ResponseEntity.ok(user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/auth/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy session
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/api/auth/checkusersession")
    public String checkUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        log.error("session is {}", session);
        User user = null;
        if (session != null && session.getAttribute("username") != null) {
            // log.error("inside checkuser, session is not null, username is not null,
            // username is {}",
            // session.getAttribute("username"));
            user = userService.getUserByUsername(session.getAttribute("username").toString()).orElse(null);
            return user.getUsername();
        } else {
            return "";
        }
    }

    @GetMapping("/api/users")
    List<User> findAll() {
        return userRepository.findAll();
    }

}
