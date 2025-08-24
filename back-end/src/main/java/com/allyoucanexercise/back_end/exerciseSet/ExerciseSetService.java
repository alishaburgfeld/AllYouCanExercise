package com.allyoucanexercise.back_end.exerciseSet;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExerciseSetService {
    private final ExerciseSetRepository ExerciseSetRepository;

    public ExerciseSetService(ExerciseSetRepository ExerciseSetRepository) {
        this.ExerciseSetRepository = ExerciseSetRepository;
    }

    public ExerciseSet getExerciseSetById(Long id) {
        return ExerciseSetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout Exercise not found"));
    }

    public ExerciseSet saveExerciseSet(ExerciseSet exerciseSet) {
        return ExerciseSetRepository.save(exerciseSet);
    }

    public ExerciseSet saveExerciseSet(WorkoutExercise workoutExercise, Integer setOrder, Integer reps, Float weight,
            Integer durationSeconds, Float distanceMeters, String distanceMeasurement, Float pacePerMile) {
        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setWorkoutExercise(workoutExercise);
        exerciseSet.setSetOrder(setOrder);
        exerciseSet.setReps(reps);
        exerciseSet.setWeight(weight);
        exerciseSet.setDurationSeconds(durationSeconds);
        exerciseSet.setDistanceMeters(distanceMeters);
        exerciseSet.setDistanceMeasurement(distanceMeasurement);
        exerciseSet.setPacePerMile(pacePerMile);
        return ExerciseSetRepository.save(exerciseSet);
    }

    public ExerciseSet updateExerciseSet(ExerciseSet exerciseSet, Long id) {
        if (!ExerciseSetRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }

        exerciseSet.setId(id); // Make sure the entity has the correct ID. This ensures that the JPA context
        // understands this is an update, not a new insert.
        return ExerciseSetRepository.save(exerciseSet);
    }

    public void deleteExerciseSet(Long id) {
        if (!ExerciseSetRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }
        ExerciseSetRepository.deleteById(id);
    }

}
