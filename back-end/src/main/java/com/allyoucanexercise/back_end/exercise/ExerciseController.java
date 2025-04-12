package com.allyoucanexercise.back_end.exercise;

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
// import org.springframework.web.server.ResponseStatusException;

import com.allyoucanexercise.back_end.ExerciseApplication;
// import com.allyoucanexercise.back_end.exercise.ExerciseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(final ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    // @Slf4j Lombok annotation to add a logger, put annotation on top of class.
    private static final Logger log = LoggerFactory.getLogger(ExerciseApplication.class);

    // @GetMapping()
    // List<Exercise> findAll() {
    // return exerciseService.findAll();
    // }

    @GetMapping("/{id}")
    Exercise findById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    void create(@Valid @RequestBody Exercise exercise) {
        exerciseService.saveExercise(exercise);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Exercise newExercise, @PathVariable Long id) {
        exerciseService.updateExercise(newExercise, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Could do ok here... is there a better response?
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
    }

    @GetMapping("/group/{exercise_group}")
    List<Exercise> findByExerciseGroup(@PathVariable String exercise_group) {
        // log.info("group is", exercise_group);
        ExerciseGroup exerciseGroup = ExerciseGroup.valueOf(exercise_group.toUpperCase());
        return exerciseService.getExercisesByGroup(exerciseGroup);
    }

}
