package com.allyoucanexercise.back_end.exercise;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return exerciseRepository.findById(id);

    }
}
