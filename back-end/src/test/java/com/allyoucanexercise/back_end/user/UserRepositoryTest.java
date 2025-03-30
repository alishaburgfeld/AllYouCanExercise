package com.allyoucanexercise.back_end.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.fail;
// import static org.mockito.ArgumentMatchers.anyList;
// import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
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
                // public Optional<User> findByUsername(String username) {
                // return jdbcClient.sql("SELECT id,username,password FROM user WHERE username =
                // :username")
                // .param("username", username)
                // .query(User.class)
                // .optional();
                // }

                // when(jdbcClient
                // .sql("SELECT id,name,user_group,user_type,description,picture FROM user WHERE
                // id = :id"))
                // .thenReturn(statementSpec);
                // when(statementSpec.param("id", 1)).thenReturn(statementSpec);
                // when(statementSpec.query(User.class)).thenReturn(mappedQuerySpec);
                // when(mappedQuerySpec.optional()).thenReturn(Optional.of(user));

                // Optional<User> result = userRepository.findById(1);

                // assertEquals("Push-up", result.get().name());
                // assertEquals(UserGroup.CHEST, result.get().userGroup());

                // verify(jdbcClient)
                // .sql("SELECT id,name,user_group,user_type,description,picture FROM user WHERE
                // id = :id");
                // verify(statementSpec).param("id", 1);
                // verify(statementSpec).query(User.class);
                // verify(mappedQuerySpec).optional(); // Ensure optional was called
        }

        // @Test
        // @DisplayName("test create - Happy Path")
        // void testCreate() {
        // when(jdbcClient
        // .sql("INSERT INTO user(name,user_group,user_type,description,picture)
        // values(?,?,?,?,?)"))
        // .thenReturn(statementSpec);
        // when(statementSpec.params(List.of(user.name(), user.userGroup().toString(),
        // user.userType().toString(), user.description(), user.picture())))
        // .thenReturn(statementSpec);
        // when(statementSpec.update()).thenReturn(1);

        // userRepository.create(user);

        // verify(jdbcClient)
        // .sql("INSERT INTO user(name,user_group,user_type,description,picture)
        // values(?,?,?,?,?)");
        // verify(statementSpec).params(List.of(user.name(),
        // user.userGroup().toString(),
        // user.userType().toString(), user.description(), user.picture()));
        // verify(statementSpec).update();
        // }

        // @Test
        // @DisplayName("test create - Unhappy Path")
        // void testCreateUnhappy() {
        // user = new User(1, "", UserGroup.CHEST, UserType.UPPERBODY, "Push-up
        // description",
        // "pictureUrl"); // Missing name

        // when(jdbcClient
        // .sql("INSERT INTO user(name,user_group,user_type,description,picture)
        // values(?,?,?,?,?)"))
        // .thenReturn(statementSpec);
        // // missing the name
        // when(statementSpec.params(List.of(user.name(), user.userGroup().toString(),
        // user.userType().toString(), user.description(), user.picture())))
        // .thenReturn(statementSpec);
        // when(statementSpec.update()).thenReturn(0);

        // try {
        // userRepository.create(user);
        // fail("Expected an exception to be thrown due to unsuccessful creation");
        // } catch (IllegalStateException e) {
        // // Verify that the exception message contains the expected failure reason
        // assertTrue(e.getMessage().contains("Failed to create user"));
        // }

        // verify(jdbcClient)
        // .sql("INSERT INTO user(name,user_group,user_type,description,picture)
        // values(?,?,?,?,?)");
        // verify(statementSpec).params(List.of(user.name(),
        // user.userGroup().toString(),
        // user.userType().toString(), user.description(), user.picture()));
        // verify(statementSpec).update();
        // }

}
