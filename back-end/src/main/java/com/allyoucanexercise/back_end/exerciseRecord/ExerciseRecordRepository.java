package com.allyoucanexercise.back_end.exerciseRecord;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allyoucanexercise.back_end.exerciseRecord.ExerciseRecord;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.exercise.Exercise;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {

    Optional<ExerciseRecord> findByUserAndExercise(User user, Exercise exercise);
}
