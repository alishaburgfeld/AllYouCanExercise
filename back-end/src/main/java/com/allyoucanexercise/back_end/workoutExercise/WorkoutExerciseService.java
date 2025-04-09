package com.allyoucanexercise.back_end.workoutExercise;

import org.springframework.stereotype.Service;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository) {

        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public Integer saveWorkoutExercise(int workoutId, int exerciseId, int exerciseOrder) {
        // should this be in its own WorkoutExerciseService?
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkoutId(workoutId);
        workoutExercise.setExerciseId(exerciseId);
        workoutExercise.setExerciseOrder(exerciseOrder);
        Integer workoutExerciseId = workoutExerciseRepository.create(workoutExercise);
        return workoutExerciseId;
    }
}
