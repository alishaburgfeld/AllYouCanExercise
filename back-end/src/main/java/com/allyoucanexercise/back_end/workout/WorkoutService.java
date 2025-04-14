package com.allyoucanexercise.back_end.workout;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// import com.allyoucanexercise.back_end.workout.WorkoutRepository;

import jakarta.persistence.EntityNotFoundException;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserService;
import com.allyoucanexercise.back_end.exercise.ExerciseService;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetService;
import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseService;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserService userService;
    private final WorkoutExerciseService workoutExerciseService;
    private final ExerciseService exerciseService;
    private final ExerciseSetService exerciseSetService;
    private static final Logger log = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(WorkoutRepository workoutRepository, UserService userService,
            WorkoutExerciseService workoutExerciseService, ExerciseService exerciseService,
            ExerciseSetService exerciseSetService) {
        this.workoutRepository = workoutRepository;
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.exerciseSetService = exerciseSetService;
        this.workoutExerciseService = workoutExerciseService;
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));
    }

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public Workout updateWorkout(Workout workout, Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new EntityNotFoundException("Workout with id " + id + " not found");
        }

        workout.setId(id);
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new EntityNotFoundException("Workout with id " + id + " not found");
        }
        workoutRepository.deleteById(id);
    }

    public List<Workout> getWorkoutsByUsername(String username) {
        // what exception does this throw?
        User user = userService.getUserByUsername(username).orElse(null);
        List<Workout> workouts = workoutRepository.findByUser(user);
        return workouts;
    }

    public Workout saveWorkout(User user, String title, LocalDateTime completedAt, String workoutNotes) {
        Workout workout = new Workout();
        workout.setUser(user);
        workout.setTitle(title);
        workout.setCompletedAt(completedAt);
        workout.setWorkoutNotes(workoutNotes);
        return workout;
    }

    public void saveFullWorkout(WorkoutRequestDTO workoutRequestDTO) {
        WorkoutDetailsDTO workoutDetailsDTO = workoutRequestDTO.getWorkoutDetails();
        List<WorkoutExerciseDetailsDTO> workoutExerciseDetails = workoutRequestDTO.getWorkoutExerciseDetails();
        User user = userService.getUserByUsername(workoutDetailsDTO.getUsername()).orElse(null);

        Workout workout = this.saveWorkout(user, workoutDetailsDTO.getTitle(), workoutDetailsDTO.getCompletedAt(),
                workoutDetailsDTO.getWorkoutNotes());

        for (int i = 0; i < workoutExerciseDetails.size(); i++) {
            WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO = workoutExerciseDetails.get(i);
            Exercise exercise = exerciseService.getExerciseById(workoutExerciseDetailsDTO.getExerciseId());
            Integer exerciseOrder = i + 1;
            WorkoutExercise workoutExercise = workoutExerciseService.saveWorkoutExercise(workout, exercise,
                    exerciseOrder);

            List<ExerciseSetDTO> exerciseSetDTOs = workoutExerciseDetailsDTO.getSets();
            for (int j = 0; j < exerciseSetDTOs.size(); j++) {
                Integer setOrder = j;
                ExerciseSetDTO setDTO = exerciseSetDTOs.get(j);
                exerciseSetService.saveExerciseSet(workoutExercise, setOrder, setDTO.getReps(), setDTO.getWeight(),
                        setDTO.getDurationSeconds(), setDTO.getDistanceMeters());

            }
        }
    }

}
