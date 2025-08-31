package com.allyoucanexercise.back_end.exerciseSet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {

    public Optional<ExerciseSet> findById(Long id);

    public List<ExerciseSet> findAllByWorkoutExercise(WorkoutExercise workoutExercise);
}
