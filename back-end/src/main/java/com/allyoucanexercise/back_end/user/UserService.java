package com.allyoucanexercise.back_end.user;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
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

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException("Username is already taken.");
        }
    }

    public void setSession(String username, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("username", username); // Store username in session
    }

    public boolean authenticateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && passwordEncoder.matches(rawPassword, user.getPassword());

    }

    // public User saveUser(User user) {
    // User savedUser;
    // try {
    // savedUser = userRepository.save(user);
    // } catch (DataIntegrityViolationException e) {
    // throw new UsernameAlreadyExistsException("Username is already taken.");
    // }
    // return savedUser;
    // }
}
