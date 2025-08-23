package com.allyoucanexercise.back_end.exerciseRecord;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allyoucanexercise.back_end.exerciseRecord.ExerciseRecord;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {

    public Optional<ExerciseRecord> findByUsername(String username);
}
