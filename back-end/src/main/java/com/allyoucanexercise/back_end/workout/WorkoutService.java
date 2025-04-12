package com.allyoucanexercise.back_end.workout;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// import com.allyoucanexercise.back_end.workout.WorkoutRepository;

import jakarta.persistence.EntityNotFoundException;

import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserService;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(WorkoutRepository workoutRepository, UserService userService) {
        this.workoutRepository = workoutRepository;
        this.userService = userService;
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
        List<Workout> workouts = workoutRepository.findByUserId(user.getId());
        return workouts;
    }
}
