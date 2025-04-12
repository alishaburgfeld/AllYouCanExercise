package com.allyoucanexercise.back_end.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private final String hashedPassword = "hashedPassword123";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username1");
        user.setPassword("password1");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("test register - happy Path")
    void testRegisterUser() {
        User newUser = user;
        newUser.setPassword(hashedPassword);

        when(userRepository.save(newUser)).thenReturn(newUser);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(hashedPassword);

        // userService.registerUser("username1", "password1");
        assertDoesNotThrow(() -> userService.registerUser(user.getUsername(), user.getPassword()));

        assertEquals(newUser.getUsername(), "username1");
        assertEquals(newUser.getPassword(), hashedPassword);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("test register fails when username exists")
    void testRegisterFailsWhenUserExists() {
        // have to use any, not user because "If I use 'user', Mockito is expecting the
        // exact same object (user) to be passed as the argument in the save method, but
        // register user creates a new instance of User with a different reference, not
        // the user object you mocked."
        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.registerUser("username1", "password1");
        });

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("test register fails when username is missing")
    void testRegisterFailsWhenUsernameMissing() {
        User invalidUser = user;
        invalidUser.setUsername(null);

        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate username"));

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.registerUser(invalidUser.getUsername(), invalidUser.getPassword());
        });
    }

    @Test
    @DisplayName("test authenticate - happy Path")
    void testAuthenticateUser() {
        String originalPassword = "password";
        user.setPassword(hashedPassword);
        when(bCryptPasswordEncoder.matches(originalPassword, hashedPassword)).thenReturn(true);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        boolean authenticated = userService.authenticateUser(user.getUsername(), originalPassword);
        verify(bCryptPasswordEncoder, times(1)).matches(originalPassword, hashedPassword);
        verify(userRepository).findByUsername(user.getUsername());
        assertTrue(authenticated);
    }

}
