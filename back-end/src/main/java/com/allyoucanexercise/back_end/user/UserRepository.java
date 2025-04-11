package com.allyoucanexercise.back_end.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findById(Long id);

    public Optional<User> findByUsername(String username);

}
