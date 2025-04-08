package com.allyoucanexercise.back_end.workout;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.allyoucanexercise.back_end.workout.WorkoutRepository;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseRepository;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserRepository;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public List<Workout> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        List<Workout> workouts = workoutRepository.findByUserId(user.getId());
        return workouts;
    }

    public void saveWorkoutExercise(int workoutId, int exerciseId, int exerciseOrder) {
        // should this be in its own WorkoutExerciseService?
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkoutId(workoutId);
        workoutExercise.setExerciseId(exerciseId);
        workoutExercise.setExerciseOrder(exerciseOrder);
        workoutExerciseRepository.create(workoutExercise);
    }

    public void saveExerciseSet(int workoutExerciseId, Integer sets, Integer reps, Float weight, int durationSeconds,
            int distanceMeters) {
        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setSets(sets);
        exerciseSet.setReps(reps);
        exerciseSet.setWeight(weight);
        exerciseSet.setDurationSeconds(durationSeconds);
        exerciseSet.setDistanceMeters(distanceMeters);

        exerciseSetRepository.save(exerciseSet);
    }

    public void saveFullWorkout(List<WorkoutIndividualExerciseDetailsDTO> fullWorkoutDetails) {
        for (WorkoutIndividualExerciseDetailsDTO individualExerciseDetails : fullWorkoutDetails) {
            // first I need to save the workout and get the workoutId.. figure out how I'm
            // going to get that workoutId... also I need to figure out how to make each
            // entry into the database unique. certain combos also have to be unique.
            int workoutId = WorkoutExerciseRepository.returnIdGivenWorkoutAndExerciseIds();

            saveWorkoutExercise(individualExerciseDetails.getWorkoutId(), individualExerciseDetails.getExerciseId(),
                    individualExerciseDetails.getExerciseOrder());
            int workoutExerciseId = WorkoutExerciseRepository.returnIdGivenWorkoutAndExerciseIds();
            saveExerciseSet(workoutExerciseId, individualExerciseDetails.getSets(), individualExerciseDetails.getReps(),
                    individualExerciseDetails.getWeight(), individualExerciseDetails.getDurationSeconds(),
                    individualExerciseDetails.getDistanceMeters());
        }
    }
}
