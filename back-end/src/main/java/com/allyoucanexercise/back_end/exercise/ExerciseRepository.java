package com.allyoucanexercise.back_end.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class ExerciseRepository {
    private List<Exercise> exercises = new ArrayList<>();

    List<Exercise> findAll() {
        return exercises;
    }

    Optional<Exercise> findById(Integer id) {
        // Integer index = id-1;
        // return Optional.of(exercises.get(index));
        return exercises.stream().filter(exercise->exercise.id()==id).findFirst();
    }

    @PostConstruct
    private void init() {
        exercises.add(new Exercise(1, "bicep curls", Group.UPPERBODY, "Hold dumbbells in your hand and pull them to your chest"));
        exercises.add(new Exercise(2, "hammer curls", Group.UPPERBODY, "Hold dumbbells in your hands, palms facing each other and pull them to your shoulders"));
    }
}
