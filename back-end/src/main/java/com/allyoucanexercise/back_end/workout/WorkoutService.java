package com.allyoucanexercise.back_end.workout;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.allyoucanexercise.back_end.workout.WorkoutRepository;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseService;

import jakarta.validation.constraints.NotNull;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetService;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserRepository;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseService workoutExerciseService;
    private final ExerciseSetService exerciseSetService;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(WorkoutRepository workoutRepository, WorkoutExerciseService workoutExerciseService,
            UserRepository userRepository, ExerciseSetService exerciseSetService) {
        this.workoutRepository = workoutRepository;
        this.workoutExerciseService = workoutExerciseService;
        this.userRepository = userRepository;
        this.exerciseSetService = exerciseSetService;
    }

    public List<Workout> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        List<Workout> workouts = workoutRepository.findByUserId(user.getId());
        return workouts;
    }

    public Integer saveWorkout(Integer userId, String title, LocalDateTime completedAt) {
        Workout workout = new Workout();
        workout.setUserId(userId);
        workout.setTitle(title);
        workout.setCompletedAt(completedAt);
        Integer workoutId = workoutRepository.create(workout);
        return workoutId;
    }

    public void saveFullWorkout(String username, String title, LocalDateTime completedAt,
            List<WorkoutIndividualExerciseDetailsDTO> fullWorkoutDetails) {
        int exerciseOrder = 1;
        Optional<User> user = userRepository.findByUsername(username);
        int userId = user.getId();
        Integer workoutId = saveWorkout(userId, title, completedAt);
        for (WorkoutIndividualExerciseDetailsDTO individualExerciseDetails : fullWorkoutDetails) {

            // [{"exerciseId": 1,"sets": 4, "reps": 10, "weight": 45},
            // {"exerciseId": 2, "duration": 20}]

            // I need to figure out how to make each
            // entry into the database unique. certain combos also have to be unique.
            individualExerciseDetails.setExerciseOrder(exerciseOrder);

            Integer workoutExerciseId = workoutExerciseService.saveWorkoutExercise(
                    individualExerciseDetails.getWorkoutId(),
                    individualExerciseDetails.getExerciseId(),
                    individualExerciseDetails.getExerciseOrder());

            exerciseSetService.saveExerciseSet(workoutExerciseId, individualExerciseDetails.getSets(),
                    individualExerciseDetails.getReps(),
                    individualExerciseDetails.getWeight(), individualExerciseDetails.getDurationSeconds(),
                    individualExerciseDetails.getDistanceMeters());

            exerciseOrder += 1;
        }
    }
}
