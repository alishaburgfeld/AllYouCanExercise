package com.allyoucanexercise.back_end.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.simple.JdbcClient;

public class UserRepositoryTest {

        @Mock
        private JdbcClient jdbcClient;

        @InjectMocks
        private UserRepository userRepository;

        // essential JdbcClient Mocks we'll use for most tests
        private JdbcClient.StatementSpec statementSpec;
        private JdbcClient.MappedQuerySpec<User> mappedQuerySpec;
        private JdbcClient.ResultQuerySpec resultQuerySpec;
        private User user;
        private User user2;

        @BeforeEach
        void setUp() {
                user = new User();
                user.setUsername("username1");
                user.setPassword("password1");
                user2 = new User();
                user2.setUsername("username2");
                user2.setPassword("password2");
                // Initializes mocks
                MockitoAnnotations.openMocks(this);
                statementSpec = mock(JdbcClient.StatementSpec.class);
                mappedQuerySpec = mock(JdbcClient.MappedQuerySpec.class);
                resultQuerySpec = mock(JdbcClient.ResultQuerySpec.class); // Mock MappedQuerySpec
        }

        @Test
        void testFindAll() {
                when(jdbcClient.sql("select * from user")).thenReturn(statementSpec);
                when(statementSpec.query(User.class)).thenReturn(mappedQuerySpec);
                when(mappedQuerySpec.list()).thenReturn(List.of(user));

                List<User> users = userRepository.findAll();

                assertNotNull(users);
                assertEquals(1, users.size());
                assertEquals("username1", users.get(0).getUsername());

                verify(jdbcClient).sql("select * from user");
                verify(statementSpec).query(User.class); // Verify query() was called with User.class
                verify(mappedQuerySpec).list(); // Verify that list() was called on the MappedQuerySpec
        }

        @Test
        void testFindByUsername() {

                when(jdbcClient
                                .sql("SELECT id,username,password FROM user WHERE username = :username"))
                                .thenReturn(statementSpec);
                when(statementSpec.param("username", user.getUsername())).thenReturn(statementSpec);
                when(statementSpec.query(User.class)).thenReturn(mappedQuerySpec);
                when(mappedQuerySpec.optional()).thenReturn(Optional.of(user));

                Optional<User> result = userRepository.findByUsername(user.getUsername());

                assertEquals("username1", result.get().getUsername());
                assertEquals(user.getPassword(), result.get().getPassword());

                verify(jdbcClient)
                                .sql("SELECT id,username,password FROM user WHERE username = :username");
                verify(statementSpec).param("username", "username1");
                verify(statementSpec).query(User.class);
                verify(mappedQuerySpec).optional();
        }

        @Test
        @DisplayName("test save - Happy Path")
        void testSave() {
                when(jdbcClient
                                .sql("INSERT INTO user(username,password) values(?,?)"))
                                .thenReturn(statementSpec);
                when(statementSpec.params(List.of(user.getUsername(), user.getPassword())))
                                .thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(1);

                userRepository.save(user);

                verify(jdbcClient)
                                .sql("INSERT INTO user(username,password) values(?,?)");
                verify(statementSpec).params(List.of(user.getUsername(), user.getPassword()));
                verify(statementSpec).update();
        }

        @Test
        @DisplayName("test save - Unhappy Path")
        void testCreateUnhappy() {
                User invalidUser = new User();
                invalidUser.setUsername("");
                invalidUser.setPassword("password");

                when(jdbcClient
                                .sql("INSERT INTO user(username,password) values(?,?)"))
                                .thenReturn(statementSpec);
                when(statementSpec.params(List.of(invalidUser.getUsername(), invalidUser.getPassword())))
                                .thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(0);

                try {
                        userRepository.save(invalidUser);
                        fail("Expected an exception to be thrown due to unsuccessful creation");
                } catch (IllegalStateException e) {
                        assertTrue(e.getMessage().contains("Failed to create user"));
                }

                verify(jdbcClient)
                                .sql("INSERT INTO user(username,password) values(?,?)");
                verify(statementSpec).params(List.of(invalidUser.getUsername(), invalidUser.getPassword()));
                verify(statementSpec).update();
        }

}
