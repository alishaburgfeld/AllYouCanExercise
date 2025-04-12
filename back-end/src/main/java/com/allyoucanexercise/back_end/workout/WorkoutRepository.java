package com.allyoucanexercise.back_end.workout;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    public Optional<Workout> findById(Long id);

    List<Workout> findByUserId(Long userId);

}
