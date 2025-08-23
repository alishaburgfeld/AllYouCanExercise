package com.allyoucanexercise.back_end.exerciseRecord;

import java.util.List;
import java.util.Optional;

// import java.util.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.exerciseRecord.ExerciseRecordRepository;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetRepository;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExerciseRecordService {

    private final ExerciseRecordRepository exerciseRecordRepository;

    public ExerciseRecordService(ExerciseRecordRepository exerciseRecordRepository) {
        this.exerciseRecordRepository = exerciseRecordRepository;
    }

    public Optional<ExerciseRecord> getExerciseRecordByUsername(String username) {
        return exerciseRecordRepository.findByUsername(username);
    }

    public ExerciseRecord saveExerciseRecord(Exercise exercise, WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO) {
        List<ExerciseSetDTO> exerciseSetDTOs = workoutExerciseDetailsDTO.getSets();
        return ExerciseRecordRepository.save(exerciseRecord);
    }
}
