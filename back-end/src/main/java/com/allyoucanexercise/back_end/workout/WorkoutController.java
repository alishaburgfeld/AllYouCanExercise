package com.allyoucanexercise.back_end.workout;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allyoucanexercise.back_end.ExerciseApplication;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(final WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    private static final Logger log = LoggerFactory.getLogger(ExerciseApplication.class);

    @GetMapping()
    List<Workout> findAll() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("/{id}")
    Workout findById(@PathVariable Long id) {
        return workoutService.getWorkoutById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    void create(@Valid @RequestBody Workout workout) {
        workoutService.saveWorkout(workout);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Workout newWorkout, @PathVariable Long id) {
        workoutService.updateWorkout(newWorkout, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Could do ok here... is there a better response?
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
    }

    @GetMapping("/user/{username}")
    List<Workout> findByUsername(@PathVariable String username) {
        return workoutService.getWorkoutsByUsername(username);
    }
}
