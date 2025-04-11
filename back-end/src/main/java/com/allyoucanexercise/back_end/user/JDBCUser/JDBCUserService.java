package com.allyoucanexercise.back_end.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password) throws UsernameAlreadyExistsException {
        log.info("in register user");
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser != null) {
            throw new UsernameAlreadyExistsException("Username '" + existingUser.getUsername() + "' is already taken.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        log.info("user is", user.getUsername());
        userRepository.save(user);
    }

    public void setSession(String username, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("username", username); // Store username in session
    }

    public boolean authenticateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && passwordEncoder.matches(rawPassword, user.getPassword());
    }

}
