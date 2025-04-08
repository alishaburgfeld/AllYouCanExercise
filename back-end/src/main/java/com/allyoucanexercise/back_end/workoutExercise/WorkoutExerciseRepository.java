package com.allyoucanexercise.back_end.workoutExercise;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.allyoucanexercise.back_end.workout.Workout;
import com.allyoucanexercise.back_end.workout.WorkoutRepository;

@Repository
public class WorkoutExerciseRepository {
    private final JdbcClient jdbcClient;

    private static final Logger log = LoggerFactory.getLogger(WorkoutRepository.class);

    public WorkoutExerciseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void create(WorkoutExercise workoutExercise) {
        // if (workoutExercise.getUserId() == null) {
        // throw new IllegalArgumentException("userId must not be null");
        // }
    }

    // public int returnIdGivenWorkoutAndExerciseIds(int exerciseId, int workoutId)
    // {
    // check DB for workout_exercise with the exercise_id of X and the workout_id of
    // Y
    // WorkoutExercise workoutExercise =
    // return workoutExercise.id
    // }
}
