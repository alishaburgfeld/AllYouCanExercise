package com.allyoucanexercise.back_end.user;

import org.apache.el.stream.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password) throws UsernameAlreadyExistsException {
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser != null) {
            throw new UsernameAlreadyExistsException("Username '" + existingUser.getUsername() + "' is already taken.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public boolean authenticateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
