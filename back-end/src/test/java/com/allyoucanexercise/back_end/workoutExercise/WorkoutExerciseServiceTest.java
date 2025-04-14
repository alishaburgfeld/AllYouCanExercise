// package com.allyoucanexercise.back_end.workoutExercise;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.dao.DataIntegrityViolationException;

// import com.allyoucanexercise.back_end.exercise.Exercise;
// import com.allyoucanexercise.back_end.exercise.ExerciseGroup;
// import com.allyoucanexercise.back_end.exercise.ExerciseService;
// import com.allyoucanexercise.back_end.exercise.ExerciseType;
// import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetService;
// import com.allyoucanexercise.back_end.user.User;
// import com.allyoucanexercise.back_end.user.UserService;
// import com.allyoucanexercise.back_end.workout.Workout;
// import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
// import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseService;

// import jakarta.persistence.EntityNotFoundException;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.List;
// import java.util.Optional;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// class WorkoutExerciseServiceTest {

// @Mock
// private WorkoutExerciseRepository workoutExerciseRepository;

// // @Mock
// // private ExerciseService exerciseService;

// @InjectMocks
// private WorkoutExerciseService workoutExerciseService;

// private Workout workout;
// private User user;
// private Exercise chestExercise;
// private final String fixedTimeString = "2025-04-02 10:30:00";
// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
// HH:mm:ss");
// LocalDateTime time = LocalDateTime.parse(fixedTimeString, formatter);

// private Workout setupCompletedWorkout(User user, String title, LocalDateTime
// completedAt, String workoutNotes) {
// Workout temporaryWorkout = new Workout();
// temporaryWorkout.setUser(user);
// temporaryWorkout.setTitle(title);
// temporaryWorkout.setCompletedAt(completedAt);
// temporaryWorkout.setWorkoutNotes(workoutNotes);
// return temporaryWorkout;
// }

// @BeforeEach
// void setUp() {
// user = new User();
// user.setUsername("username1");
// user.setPassword("password1");
// chestExercise = new Exercise(
// "Push Up",
// ExerciseGroup.CHEST,
// ExerciseType.UPPERBODY,
// "A basic push up");
// workout = setupCompletedWorkout(user, "Test Title1", time, "Workout Notes");
// MockitoAnnotations.openMocks(this);
// }
// }
