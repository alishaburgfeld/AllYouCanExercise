package com.allyoucanexercise.back_end.workout;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.allyoucanexercise.back_end.workout.WorkoutRepository;
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
}
