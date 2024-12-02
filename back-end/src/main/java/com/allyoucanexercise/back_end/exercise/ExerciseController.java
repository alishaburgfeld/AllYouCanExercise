package com.allyoucanexercise.back_end.exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("")
    List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @GetMapping("/{id}")
    Exercise findById(@PathVariable Integer id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        if(exercise.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found.");
        }
        return exercise.get();
        // I don't understand why the .get is needed here
    }

    // 1:08 in the video
}
