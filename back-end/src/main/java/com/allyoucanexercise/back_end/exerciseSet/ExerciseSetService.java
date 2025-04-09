package com.allyoucanexercise.back_end.exerciseSet;

import org.springframework.stereotype.Service;

@Service
public class ExerciseSetService {

    public final ExerciseSetRepository exerciseSetRepository;

    public ExerciseSetService(ExerciseSetRepository exerciseSetRepository) {
        this.exerciseSetRepository = exerciseSetRepository;
    }

    public void saveExerciseSet(int workoutExerciseId, Integer sets, Integer reps, Float weight, int durationSeconds,
            int distanceMeters) {
        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setSets(sets);
        exerciseSet.setReps(reps);
        exerciseSet.setWeight(weight);
        exerciseSet.setDurationSeconds(durationSeconds);
        exerciseSet.setDistanceMeters(distanceMeters);

        exerciseSetRepository.create(exerciseSet);
    }

}
