package com.allyoucanexercise.back_end.user;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void registerUser(String username, String password) throws UsernameAlreadyExistsException {
        // is there a different way to address this since I will get a
        // dataIntegrityViolation if it exists?
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser != null) {
            throw new UsernameAlreadyExistsException("Username '" + existingUser.getUsername() + "' is already taken.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        // log.info("user is", user.getUsername());
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
