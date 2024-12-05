package com.allyoucanexercise.back_end.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.allyoucanexercise.back_end.ExerciseApplication;

import jakarta.annotation.PostConstruct;

@Repository
public class ExerciseRepository {
    private List<Exercise> exercises = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(ExerciseApplication.class);


    List<Exercise> findAll() {
        log.info("inside the findall inside repository");
        return exercises;
    }

    Optional<Exercise> findById(Integer id) {
        return exercises.stream().filter(exercise->exercise.id()==id).findFirst();
    }

    void create (Exercise exercise) {
        log.info("in the create repository method");
        try {
            exercises.add(exercise);
        }
        catch (Exception e) {
            log.info("error adding", e);
        }
    }

    @PostConstruct
    private void init() {
        exercises.add(new Exercise(1, "bicep curls", Group.UPPERBODY, "Hold dumbbells in your hand and pull them to your chest"));
        exercises.add(new Exercise(2, "hammer curls", Group.UPPERBODY, "Hold dumbbells in your hands, palms facing each other and pull them to your shoulders"));
    }
}
