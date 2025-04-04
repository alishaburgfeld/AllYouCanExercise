package com.allyoucanexercise.back_end.exercise;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.server.ResponseStatusException;

import com.allyoucanexercise.back_end.ExerciseApplication;
import com.allyoucanexercise.back_end.exercise.ExerciseRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;

    public ExerciseController(final ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    // @Slf4j Lombok annotation to add a logger, put annotation on top of class.
    private static final Logger log = LoggerFactory.getLogger(ExerciseApplication.class);

    @GetMapping()
    List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @GetMapping("/{id}")
    Exercise findById(@PathVariable Integer id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        if (exercise.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found.");
        }
        return exercise.get();
        // I don't understand why the .get is needed here:
        // Since exercise can be an optional you have to add the .get()
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    void create(@Valid @RequestBody Exercise exercise) {
        exerciseRepository.create(exercise);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Exercise newExercise, @PathVariable Integer id) {
        Optional<Exercise> existingExercise = exerciseRepository.findById(id);
        if (existingExercise.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found.");
        }
        exerciseRepository.update(newExercise, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Could do ok here... is there a better response?
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        exerciseRepository.delete(id);
    }

    @GetMapping("/group/{exercise_group}")
    List<Exercise> findByExerciseGroup(@PathVariable String exercise_group) {
        // log.info("group is", exercise_group);
        return exerciseRepository.findByExerciseGroup(exercise_group);
    }

}
