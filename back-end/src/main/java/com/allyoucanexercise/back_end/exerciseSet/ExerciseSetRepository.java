package com.allyoucanexercise.back_end.exerciseSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.allyoucanexercise.back_end.workout.WorkoutRepository;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;

@Repository
public class ExerciseSetRepository {
    private final JdbcClient jdbcClient;

    private static final Logger log = LoggerFactory.getLogger(WorkoutRepository.class);

    public ExerciseSetRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void create(ExerciseSet exerciseSet) {
        // if (workoutExercise.getUserId() == null) {
        // throw new IllegalArgumentException("userId must not be null");
        // }
    }
}
