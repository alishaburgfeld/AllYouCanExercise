package com.allyoucanexercise.back_end.workout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(final WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    private static final Logger log = LoggerFactory.getLogger(WorkoutController.class);

    @GetMapping()
    List<Workout> findAll() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    WorkoutResponseDTO findById(@PathVariable Long id) {
        WorkoutResponseDTO fullWorkout = workoutService.getFullWorkoutAndExerciseDetailsById(id);
        return fullWorkout;
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
    List<WorkoutResponseDTO> findByUsername(@PathVariable String username) {
        List<WorkoutResponseDTO> allWorkouts = workoutService.getWorkoutsByUsername(username);
        // System.out.println("***** FULLWORKOUTdetails = " + allWorkouts);
        return allWorkouts;
    }

    @PostMapping("/full/save")
    public ResponseEntity<?> saveFullWorkout(@RequestBody WorkoutRequestDTO request) {
        // log.error("inside saveFullWorkout, request is {}", request);

        workoutService.saveFullWorkout(request);
        // return ResponseEntity.ok().build();

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);

        return ResponseEntity.ok(responseBody);
    }
}

// exercise order is just the index of the object in the array + 1
// set order is just the index of the object in the array + 1
// {
// "workoutDetails": {
// "username": "xusername",
// "title": "xtitle",
// "completedAt": "2025-04-13T14:00:00",
// "workoutNotes": "xnotes"
// },
// "workoutExerciseDetails": [
// {
// "exerciseId": 1,
// "sets": [
// { "reps": 10, "weight": 50.0},
// { "reps": 8, "weight": 55.0 }
// ]
// },
// {
// "exerciseId": 2,
// "sets": [
// { "durationSeconds": 900, "distanceMeters": 2000, "distanceMeasurement":
// "MILES" },
// ]
// }
// ]
// }

// now with set segments it needs to come in as:
// {
// "exerciseId": 1,
// "sets": [
// [{ "reps": 10, "weight": 50.0}],
// [{ "reps": 8, "weight": 55.0 }, {"reps": 2, "weight": 50.0}]
// ]
// },