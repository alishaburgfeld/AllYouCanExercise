package com.allyoucanexercise.back_end.workoutExercise;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

    public Optional<WorkoutExercise> findById(Long id);
}
