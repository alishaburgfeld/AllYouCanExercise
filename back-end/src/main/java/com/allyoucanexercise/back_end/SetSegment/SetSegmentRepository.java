package com.allyoucanexercise.back_end.SetSegment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;

public interface SetSegmentRepository extends JpaRepository<SetSegment, Long> {
    public Optional<SetSegment> findById(Long id);

    public List<SetSegment> findAllByExerciseSet(ExerciseSet exerciseSet);
}
