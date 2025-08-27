package com.allyoucanexercise.back_end.workoutExercise;

import java.util.List;
// import java.util.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.workout.Workout;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public WorkoutExercise getWorkoutExerciseById(Long id) {
        return workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout Exercise not found"));
    }

    public List<WorkoutExercise> getAllWorkoutExercisesByWorkout(Workout workout) {
        return workoutExerciseRepository.findAllByWorkout(workout);
    }

    public WorkoutExercise saveWorkoutExercise(WorkoutExercise workoutExercise) {
        return workoutExerciseRepository.save(workoutExercise);
    }

    public WorkoutExercise saveWorkoutExercise(Workout workout, Exercise exercise, Integer exerciseOrder) {
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkout(workout);
        workoutExercise.setExercise(exercise);
        workoutExercise.setExerciseOrder(exerciseOrder);
        return workoutExerciseRepository.save(workoutExercise);
    }

    public WorkoutExercise updateWorkoutExercise(WorkoutExercise workoutExercise, Long id) {
        if (!workoutExerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }

        workoutExercise.setId(id); // Make sure the entity has the correct ID. This ensures that the JPA context
        // understands this is an update, not a new insert.
        return workoutExerciseRepository.save(workoutExercise);
    }

    public void deleteWorkoutExercise(Long id) {
        if (!workoutExerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }
        workoutExerciseRepository.deleteById(id);
    }
}
