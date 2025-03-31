package com.allyoucanexercise.back_end.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.simple.JdbcClient;

public class UserServiceTest {
    @Mock
    private JdbcClient jdbcClient;

    // @Mock
    // private BCryptPasswordEncoder bCryptPasswordEncoder;
    // I was going to mock this out, but I would have had to pass the encoder into
    // my UserService similarly to how I pass in the UserRepository (not making a
    // new instance). This would set it up as a depencency and allow me to mock it
    // out, so for now I'll just let it actually create a new one for the tests
    // instead of mocking.

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // essential JdbcClient Mocks we'll use for most tests
    private JdbcClient.StatementSpec statementSpec;
    private JdbcClient.MappedQuerySpec<User> mappedQuerySpec;
    private JdbcClient.ResultQuerySpec resultQuerySpec;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username1");
        user.setPassword("password1");
        // user2 = new User();
        // user2.setUsername("username2");
        // user2.setPassword("password2");
        MockitoAnnotations.openMocks(this);
        statementSpec = mock(JdbcClient.StatementSpec.class);
        mappedQuerySpec = mock(JdbcClient.MappedQuerySpec.class);
        resultQuerySpec = mock(JdbcClient.ResultQuerySpec.class); // Mock MappedQuerySpec
    }

    @Test
    @DisplayName("test register - happy Path")
    void testRegisterUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        // Mock save behavior (since it's void, use doNothing)
        doNothing().when(userRepository).save(any(User.class));

        // This executes userService.registerUser(...) and ensures that it runs without
        // throwing an exception.
        assertDoesNotThrow(() -> userService.registerUser(user.getUsername(), user.getPassword()));

        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository).save(any(User.class));

    }
    // public void registerUser(String username, String password) throws
    // UsernameAlreadyExistsException {
    // log.info("in register user");
    // User existingUser = userRepository.findByUsername(username).orElse(null);
    // if (existingUser != null) {
    // throw new UsernameAlreadyExistsException("Username '" +
    // existingUser.getUsername() + "' is already taken.");
    // }

    // String encodedPassword = passwordEncoder.encode(password);
    // User user = new User();
    // user.setUsername(username);
    // user.setPassword(encodedPassword);
    // log.info("user is", user.getUsername());
    // userRepository.save(user);
    // }

    // @Test
    // @DisplayName("test register - unhappy Path")
    // void testRegisterUserException() {
    // when(userRepository.findByUsername("username1")).thenReturn(Optional.of(user));
    // try {
    // userService.registerUser(user.getUsername(), user.getPassword());
    // fail("Expected an exception to be thrown due to unsuccessful sign up");
    // } catch (UsernameAlreadyExistsException e) {
    // assertTrue(e.getMessage().contains("username1 is already taken"));
    // }
    // }

}
