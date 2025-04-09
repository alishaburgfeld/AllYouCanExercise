package com.allyoucanexercise.back_end.workout;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import com.allyoucanexercise.back_end.ExerciseApplication;
import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.exercise.ExerciseRepository;
import com.allyoucanexercise.back_end.user.UserRepository;
import com.allyoucanexercise.back_end.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutService workoutService;

    public WorkoutController(final UserRepository userRepository, final ExerciseRepository exerciseRepository,
            final WorkoutRepository workoutRepository, final WorkoutService workoutService) {

        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.workoutService = workoutService;
    }

    private static final Logger log = LoggerFactory.getLogger(ExerciseApplication.class);

    @GetMapping()
    List<Workout> findAll() {
        return workoutRepository.findAll();
    }

    @GetMapping("/{id}")
    Workout findById(@PathVariable Integer id) {
        Optional<Workout> workout = workoutRepository.findById(id);
        if (workout.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found.");
        }
        return workout.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    void create(@Valid @RequestBody Workout workout) {
        workoutRepository.create(workout);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Workout newWorkout, @PathVariable Integer id) {
        Optional<Workout> workout = workoutRepository.findById(id);
        if (workout.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found.");
        }
        workoutRepository.update(newWorkout, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Could do ok here... is there a better response?
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        workoutRepository.delete(id);
    }

    @GetMapping("/user/{username}")
    List<Workout> findByUsername(@PathVariable String username) {
        return workoutService.findByUsername(username);
    }

    @PostMapping("/full/save")
    ResponseEntity<?> saveFullWorkout(@RequestBody String username, @RequestBody String title,
            @RequestBody LocalDateTime completedAt,
            @RequestBody List<WorkoutIndividualExerciseDetailsDTO> fullWorkoutDetails) {
        workoutService.saveFullWorkout(username, title, completedAt, fullWorkoutDetails);
        return ResponseEntity.ok("Workout saved");
    }
}
