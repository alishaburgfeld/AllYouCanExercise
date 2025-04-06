package com.allyoucanexercise.back_end.workout;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateWorkoutScript {

    private final WorkoutRepository workoutRepository;
    private static final Logger log = LoggerFactory.getLogger(CreateWorkoutScript.class);
    private LocalDateTime currentTime = LocalDateTime.now();

    public CreateWorkoutScript(final WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public void create() {
        Workout temporaryWorkout = new Workout();
        temporaryWorkout.setUserId(2);
        temporaryWorkout.setTitle("Second User Workout Title");
        temporaryWorkout.setCompletedAt(currentTime);
        log.error("in create script, temporary workout is {}", temporaryWorkout);
        workoutRepository.create(temporaryWorkout);
    }
}
