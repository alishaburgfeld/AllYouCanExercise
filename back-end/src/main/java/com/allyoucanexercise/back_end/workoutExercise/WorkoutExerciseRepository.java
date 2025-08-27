package com.allyoucanexercise.back_end.workoutExercise;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allyoucanexercise.back_end.workout.Workout;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

    public Optional<WorkoutExercise> findById(Long id);

    public List<WorkoutExercise> findAllByWorkout(Workout workout);
}
