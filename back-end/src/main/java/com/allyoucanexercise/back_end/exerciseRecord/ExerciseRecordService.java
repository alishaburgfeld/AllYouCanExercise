package com.allyoucanexercise.back_end.exerciseRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

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
import com.allyoucanexercise.back_end.workout.Workout;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetRepository;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
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

    public ExerciseRecord saveExerciseRecord(Exercise exercise, User user, LocalDateTime lastExercised,
            Workout lastExercisedWorkout,
            Integer maxSets, Workout maxSetsWorkout, Integer maxReps,
            Workout maxRepsWorkout, Float maxWeight, Workout maxWeightWorkout,
            Float maxVolume, Workout maxVolumeWorkout,
            Integer maxDurationSeconds, Workout maxDurationWorkout, Float maxDistanceMeters, Workout maxDistanceWorkout,
            Float maxPacePerMile, Workout maxPaceWorkout) {
        ExerciseRecord exerciseRecord = new ExerciseRecord();
        exerciseRecord.setExercise(exercise);
        exerciseRecord.setUser(user);
        exerciseRecord.setLastExercised(lastExercised);
        exerciseRecord.setLastExercisedWorkout(lastExercisedWorkout);
        exerciseRecord.setMaxSets(maxSets);
        exerciseRecord.setMaxSetsWorkout(maxSetsWorkout);
        exerciseRecord.setMaxReps(maxReps);
        exerciseRecord.setMaxRepsWorkout(maxRepsWorkout);
        exerciseRecord.setMaxWeight(maxWeight);
        exerciseRecord.setMaxWeightWorkout(maxWeightWorkout);
        exerciseRecord.setMaxVolume(maxVolume);
        exerciseRecord.setMaxVolumeWorkout(maxVolumeWorkout);
        exerciseRecord.setMaxDurationSeconds(maxDurationSeconds);
        exerciseRecord.setMaxDurationWorkout(maxDurationWorkout);
        exerciseRecord.setMaxDistanceMeters(maxDistanceMeters);
        exerciseRecord.setMaxDistanceWorkout(maxDistanceWorkout);
        exerciseRecord.setMaxPacePerMile(maxPacePerMile);
        exerciseRecord.setMaxPaceWorkout(maxPaceWorkout);
        return exerciseRecordRepository.save(exerciseRecord);
    }

    public ExerciseRecord saveExerciseRecord(Workout workout, Exercise exercise,
            WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO,
            User user) {
        // always save last_exercised
        List<ExerciseSetDTO> exerciseSetDTOs = workoutExerciseDetailsDTO.getSets();
        LocalDateTime lastExercised = workout.getCompletedAt();
        Integer totalSets = exerciseSetDTOs.size();
        Integer maxRepsInWorkout = 0;
        Float maxWeightInWorkout = 0f;
        Integer maxDurationInWorkout = 0;
        Float maxDistanceInWorkout = 0f;
        Float maxPaceInWorkout = 0f;
        Float volumeInWorkout = 0f;

        for (ExerciseSetDTO set : exerciseSetDTOs) {
            if (set.getReps() != null && set.getWeight() != null) {
                volumeInWorkout += (set.getReps() * set.getWeight());
            }
            setMaxValueForWorkoutIfCurrentSetValueIsHigher(set.getReps(), maxRepsInWorkout);
            setMaxValueForWorkoutIfCurrentSetValueIsHigher(set.getWeight(), maxWeightInWorkout);
            setMaxValueForWorkoutIfCurrentSetValueIsHigher(set.getDurationSeconds(), maxDurationInWorkout);
            setMaxValueForWorkoutIfCurrentSetValueIsHigher(set.getDistanceMeters(), maxDistanceInWorkout);
            setMaxValueForWorkoutIfCurrentSetValueIsHigher(set.getPacePerMile(), maxPaceInWorkout);
        }

        Optional<ExerciseRecord> existingExerciseRecordOpt = getExerciseRecord(user, exercise);

        if (existingExerciseRecordOpt.isEmpty()) {
            return this.saveExerciseRecord(exercise, user, lastExercised, workout, totalSets, workout,
                    maxRepsInWorkout, workout, maxWeightInWorkout, workout, volumeInWorkout, workout,
                    maxDurationInWorkout, workout, maxDistanceInWorkout, workout, maxPaceInWorkout, workout);
        }

        else {
            ExerciseRecord existingExerciseRecord = existingExerciseRecordOpt.get();
            if (isWorkoutValueHigherThanExistingExerciseRecordValue(maxRepsInWorkout,
                    existingExerciseRecord.getMaxReps())) {
                updateRecordWithHigherValue(
                        maxRepsInWorkout,
                        ExerciseRecord::setMaxReps,
                        ExerciseRecord::setMaxRepsWorkout,
                        existingExerciseRecord,
                        workout);
            }

            if (isWorkoutValueHigherThanExistingExerciseRecordValue(totalSets,
                    existingExerciseRecord.getMaxSets())) {
                updateRecordWithHigherValue(
                        maxRepsInWorkout,
                        ExerciseRecord::setMaxSets,
                        ExerciseRecord::setMaxSetsWorkout,
                        existingExerciseRecord,
                        workout);
            }

            if (isWorkoutValueHigherThanExistingExerciseRecordValue(maxWeightInWorkout,
                    existingExerciseRecord.getMaxWeight())) {
                updateRecordWithHigherValue(
                        maxWeightInWorkout,
                        ExerciseRecord::setMaxWeight,
                        ExerciseRecord::setMaxWeightWorkout,
                        existingExerciseRecord,
                        workout);
            }

            if (isWorkoutValueHigherThanExistingExerciseRecordValue(volumeInWorkout,
                    existingExerciseRecord.getMaxVolume())) {
                updateRecordWithHigherValue(
                        volumeInWorkout,
                        ExerciseRecord::setMaxVolume,
                        ExerciseRecord::setMaxVolumeWorkout,
                        existingExerciseRecord,
                        workout);
            }

            if (isWorkoutValueHigherThanExistingExerciseRecordValue(maxDurationInWorkout,
                    existingExerciseRecord.getMaxDurationSeconds())) {
                updateRecordWithHigherValue(
                        maxDurationInWorkout,
                        ExerciseRecord::setMaxDurationSeconds,
                        ExerciseRecord::setMaxDurationWorkout,
                        existingExerciseRecord,
                        workout);
            }

            if (isWorkoutValueHigherThanExistingExerciseRecordValue(maxDistanceInWorkout,
                    existingExerciseRecord.getMaxDistanceMeters())) {
                updateRecordWithHigherValue(
                        maxDistanceInWorkout,
                        ExerciseRecord::setMaxDistanceMeters,
                        ExerciseRecord::setMaxDistanceWorkout,
                        existingExerciseRecord,
                        workout);
            }

            if (isWorkoutValueHigherThanExistingExerciseRecordValue(maxPaceInWorkout,
                    existingExerciseRecord.getMaxPacePerMile())) {
                updateRecordWithHigherValue(
                        maxPaceInWorkout,
                        ExerciseRecord::setMaxPacePerMile,
                        ExerciseRecord::setMaxPaceWorkout,
                        existingExerciseRecord,
                        workout);
            }
            existingExerciseRecord.setLastExercised(lastExercised);
            existingExerciseRecord.setLastExercisedWorkout(workout);
            return existingExerciseRecord;
        }
    }

    private <T extends Number & Comparable<T>> void updateRecordWithHigherValue(
            T workoutValue,
            BiConsumer<ExerciseRecord, T> setNewValue,
            BiConsumer<ExerciseRecord, Workout> setWorkout,
            ExerciseRecord existingRecord,
            Workout workout) {

        setNewValue.accept(existingRecord, workoutValue);
        setWorkout.accept(existingRecord, workout);
    }

    public boolean isWorkoutValueHigherThanExistingExerciseRecordValue(Integer workoutValue,
            Integer existingExerciseRecordValue) {
        if (workoutValue > 0 && (existingExerciseRecordValue != null) && (workoutValue > existingExerciseRecordValue)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isWorkoutValueHigherThanExistingExerciseRecordValue(Float workoutValue,
            Float existingExerciseRecordValue) {
        if (workoutValue > 0 && (existingExerciseRecordValue != null) && (workoutValue > existingExerciseRecordValue)) {
            return true;
        } else {
            return false;
        }
    }

    public void setMaxValueForWorkoutIfCurrentSetValueIsHigher(Integer setValue, Integer maxWorkoutValue) {
        if (setValue != null && setValue > maxWorkoutValue) {
            maxWorkoutValue = setValue;
        }
    }

    public void setMaxValueForWorkoutIfCurrentSetValueIsHigher(Float setValue, Float maxWorkoutValue) {
        if (setValue != null && setValue > maxWorkoutValue) {
            maxWorkoutValue = setValue;

        }
    };

    // if no exerciseRecord then automatically save the exerciseRecord.

}
