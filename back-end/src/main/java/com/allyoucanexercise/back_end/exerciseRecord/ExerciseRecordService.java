package com.allyoucanexercise.back_end.exerciseRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

// import java.util.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.exercise.ExerciseService;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserService;
import com.allyoucanexercise.back_end.workout.Workout;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;

import com.allyoucanexercise.back_end.setSegment.SetSegmentDTO;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;

@Service
public class ExerciseRecordService {

    private final ExerciseRecordRepository exerciseRecordRepository;
    private final UserService userService;
    private final ExerciseService exerciseService;

    public ExerciseRecordService(ExerciseRecordRepository exerciseRecordRepository, UserService userService,
            ExerciseService exerciseService) {
        this.exerciseRecordRepository = exerciseRecordRepository;
        this.userService = userService;
        this.exerciseService = exerciseService;
    }

    public Optional<ExerciseRecord> getExerciseRecord(User user, Exercise exercise) {
        return exerciseRecordRepository.findByUserAndExercise(user, exercise);
    }

    public ExerciseRecordResponseDTO getExerciseRecord(String username, Long exerciseId) {
        User user = userService.getUserByUsername(username).orElse(null);
        Exercise exercise = exerciseService.getExerciseById(exerciseId);
        ExerciseRecord exerciseRecord = exerciseRecordRepository.findByUserAndExercise(user, exercise).orElse(null);
        if (exerciseRecord != null) {

            ExerciseRecordResponseDTO exerciseRecordResponseDTO = new ExerciseRecordResponseDTO(
                    exerciseId,
                    exerciseRecord.getLastExercised(),
                    exerciseRecord.getLastExercisedWorkout().getId(),
                    exerciseRecord.getMaxSets(),
                    exerciseRecord.getMaxSetsWorkout().getId(),
                    exerciseRecord.getMaxReps(),
                    exerciseRecord.getMaxRepsWorkout().getId(),
                    exerciseRecord.getMaxWeight(),
                    exerciseRecord.getMaxWeightWorkout().getId(),
                    exerciseRecord.getMaxVolume(),
                    exerciseRecord.getMaxVolumeWorkout().getId(),
                    exerciseRecord.getMaxDurationSeconds(),
                    exerciseRecord.getMaxDurationWorkout().getId(),
                    exerciseRecord.getMaxDistanceMeters(),
                    exerciseRecord.getMaxDistanceWorkout().getId(),
                    exerciseRecord.getMaxPacePerMile(),
                    exerciseRecord.getMaxPaceWorkout().getId());
            return exerciseRecordResponseDTO;
        } else {
            return null;
        }
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
            Integer totalSetReps = 0;
            List<SetSegmentDTO> segments = set.getSegments();
            Boolean isCardio = false;
            for (SetSegmentDTO segment : segments) {
                if (segment.getReps() != null || segment.getWeight() != null) {
                    volumeInWorkout += (segment.getReps() * segment.getWeight());
                    totalSetReps += segment.getReps();
                    maxWeightInWorkout = setMaxValueForWorkoutIfCurrentSetValueIsHigher((float) segment.getWeight(),
                            (float) maxWeightInWorkout);
                } else {
                    isCardio = true;
                    maxDurationInWorkout = setMaxValueForWorkoutIfCurrentSetValueIsHigher(segment.getDurationSeconds(),
                            maxDurationInWorkout);
                    maxDistanceInWorkout = setMaxValueForWorkoutIfCurrentSetValueIsHigher(segment.getDistanceMeters(),
                            maxDistanceInWorkout);
                    maxPaceInWorkout = setMaxValueForWorkoutIfCurrentSetValueIsHigher(segment.getPacePerMile(),
                            maxPaceInWorkout);
                }
            }
            if (!isCardio) {
                maxRepsInWorkout = setMaxValueForWorkoutIfCurrentSetValueIsHigher(totalSetReps, maxRepsInWorkout);
            }

        }

        Optional<ExerciseRecord> existingExerciseRecordOpt = getExerciseRecord(user, exercise);

        if (existingExerciseRecordOpt.isEmpty()) {
            // System.out.println("🏋️‍♀️ ERS - record is empty.");
            return this.saveExerciseRecord(exercise, user, lastExercised, workout, totalSets, workout,
                    maxRepsInWorkout, workout, maxWeightInWorkout, workout, volumeInWorkout, workout,
                    maxDurationInWorkout, workout, maxDistanceInWorkout, workout, maxPaceInWorkout, workout);
        }

        else {
            // System.out.println("🏋️‍♀️ ERS - record is NOT empty.");
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

    public Integer setMaxValueForWorkoutIfCurrentSetValueIsHigher(Integer setValue, Integer maxWorkoutValue) {
        if (setValue != null && setValue > maxWorkoutValue) {
            maxWorkoutValue = setValue;
        }
        return maxWorkoutValue;
    }

    public Float setMaxValueForWorkoutIfCurrentSetValueIsHigher(Float setValue, Float maxWorkoutValue) {
        if (setValue != null && setValue > maxWorkoutValue) {
            maxWorkoutValue = setValue;
        }
        return maxWorkoutValue;
    };

    // if no exerciseRecord then automatically save the exerciseRecord.

}
