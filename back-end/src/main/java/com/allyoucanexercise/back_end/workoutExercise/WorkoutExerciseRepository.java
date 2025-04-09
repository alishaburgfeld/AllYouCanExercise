package com.allyoucanexercise.back_end.workoutExercise;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.allyoucanexercise.back_end.workout.Workout;
import com.allyoucanexercise.back_end.workout.WorkoutRepository;

import jakarta.validation.constraints.NotNull;

@Repository
public class WorkoutExerciseRepository {
    private final JdbcClient jdbcClient;

    private static final Logger log = LoggerFactory.getLogger(WorkoutRepository.class);

    public WorkoutExerciseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Integer create(WorkoutExercise workoutExercise) {
        if (workoutExercise.getWorkoutId() == null) {
            throw new IllegalArgumentException("workoutId must not be null");
        }

        if (workoutExercise.getExerciseId() == null) {
            throw new IllegalArgumentException("exerciseId must not be null");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int updated = jdbcClient
                .sql("INSERT INTO workout_exercise(workout_id, exercise_id, exercise_order) values (?, ?, ?)")
                .params(List.of(
                        workoutExercise.getWorkoutId(),
                        workoutExercise.getExerciseId(),
                        workoutExercise.getExerciseOrder()))
                .update(keyHolder);

        Assert.state(updated == 1, "Failed to create workout exercise");

        Number key = keyHolder.getKey();
        if (key != null) {
            return key.intValue();
        } else {
            throw new IllegalStateException("Failed to retrieve generated workout ID");
        }
    }
}
