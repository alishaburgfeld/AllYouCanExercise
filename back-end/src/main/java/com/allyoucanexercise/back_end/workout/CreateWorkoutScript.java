package com.allyoucanexercise.back_end.workout;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.workout.WorkoutService;

@Component
public class CreateWorkoutScript {

    private final WorkoutService workoutService;
    private static final Logger log = LoggerFactory.getLogger(CreateWorkoutScript.class);
    private LocalDateTime currentTime = LocalDateTime.now();

    public CreateWorkoutScript(final WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    public void createSampleWorkout() {

        User user = createSampleUser();

        Workout temporaryWorkout = new Workout();
        temporaryWorkout.setUser(user);
        temporaryWorkout.setTitle("Sample User 15 Workout Title");
        temporaryWorkout.setCompletedAt(currentTime);
        temporaryWorkout.setWorkoutNotes("this workout was really fun");
        log.error("in create script, temporary workout is {}", temporaryWorkout);
        workoutService.saveWorkout(temporaryWorkout);
    }

    public User createSampleUser() {
        long id = 15;
        User user = new User();
        user.setId(id);
        user.setUsername("sample_username");
        user.setPassword("samplePassword");
        return user;
    }
}
