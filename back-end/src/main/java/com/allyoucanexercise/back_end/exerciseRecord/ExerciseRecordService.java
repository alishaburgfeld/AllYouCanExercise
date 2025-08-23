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
import com.allyoucanexercise.back_end.user.User;
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

    public Optional<ExerciseRecord> getExerciseRecord(User user, Exercise exercise) {
        return exerciseRecordRepository.findByUserAndExercise(user, exercise);
    }

    public ExerciseRecord saveExerciseRecord(ExerciseRecord exerciseRecord) {
        return exerciseRecordRepository.save(exerciseRecord);
    }

    public ExerciseRecord saveExerciseRecord(Exercise exercise, WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO,
            User user) {

        List<ExerciseSetDTO> exerciseSetDTOs = workoutExerciseDetailsDTO.getSets();
        int totalSets = exerciseSetDTOs.size();
        int maxRepsInWorkout = 0;
        float maxWeightInWorkout = 0f;
        int maxDurationInWorkout = 0;
        float maxDistanceInWorkout = 0f;
        float volumeInWorkout = 0f;

        for (ExerciseSetDTO set : exerciseSetDTOs) {
            if (set.getReps() != null && set.getWeight() != null) {
                volumeInWorkout += (set.getReps() * set.getWeight());
            }
            if (set.getReps() != null && set.getReps() > maxRepsInWorkout) {
                maxRepsInWorkout = set.getReps();
            }
            if (set.getWeight() != null && set.getWeight() > maxWeightInWorkout) {
                maxWeightInWorkout = set.getWeight();
            }
            if (set.getDurationSeconds() != null && set.getDurationSeconds() > maxDurationInWorkout) {
                maxDurationInWorkout = set.getDurationSeconds();
            }
            if (set.getDistanceMeters() != null && set.getDistanceMeters() > maxDistanceInWorkout) {
                maxDistanceInWorkout = set.getDistanceMeters();
            }
        }

        Optional<ExerciseRecord> exerciseRecord = getExerciseRecord(user, exercise);
        // if no exerciseRecord then automatically save the exerciseRecord. if there is
        // an exercise record then need to check if any of the maxes in the workout are
        // higher than the record, then need to update it.

        return ExerciseRecordRepository.save(exerciseRecord);
    }
}
