package com.allyoucanexercise.back_end.exerciseSet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {

    public Optional<ExerciseSet> findById(Long id);
}
