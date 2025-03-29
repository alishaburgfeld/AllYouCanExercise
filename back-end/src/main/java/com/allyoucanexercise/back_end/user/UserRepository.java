package com.allyoucanuser.back_end.user;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<User> findByUsername(String username) {
        return jdbcClient.sql("SELECT id,username,password FROM user WHERE username = :username")
                .param("username", username)
                .query(User.class)
                .optional();
    }

    public void save(User user) {
        var updated = jdbcClient.sql("INSERT INTO user(username,password) values(?,?)")
                .params(List.of(user.getUsername(), user.getPassword()))
                .update();

        Assert.state(updated == 1, "Failed to create user " + user.getUsername());
    }
}
