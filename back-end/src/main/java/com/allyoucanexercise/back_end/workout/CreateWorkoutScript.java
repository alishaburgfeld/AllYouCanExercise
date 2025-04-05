package com.allyoucanexercise.back_end.workout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateWorkoutScript {

    private final WorkoutRepository workoutRepository;
    private static final Logger log = LoggerFactory.getLogger(CreateWorkoutScript.class);

    public CreateWorkoutScript(final WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    void create() {
        Workout temporaryWorkout = new Workout();
        temporaryWorkout.setUserId(1);
        temporaryWorkout.setTitle("First Workout Title");
        log.debug("in create script");
        workoutRepository.create(temporaryWorkout);
    }
}
