package com.allyoucanexercise.back_end.exerciseRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.exercise.ExerciseGroup;
import com.allyoucanexercise.back_end.exercise.ExerciseService;
import com.allyoucanexercise.back_end.exercise.ExerciseType;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserService;
import com.allyoucanexercise.back_end.workout.Workout;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ExerciseRecordTest {

    @Mock
    private ExerciseRecordRepository exerciseRecordRepository;

    @Mock
    private UserService userService;

    @Mock
    private ExerciseService exerciseService;

    @InjectMocks
    private ExerciseRecordService exerciseRecordService;

    private Exercise chestExercise;
    private Exercise cardioExercise;
    private User user;
    private ExerciseRecord cardioExerciseRecord;
    private ExerciseRecord pushUpExerciseRecord;
    private final String fixedTimeString = "2025-04-02 10:30:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime time = LocalDateTime.parse(fixedTimeString, formatter);

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username1");
        user.setPassword("password1");

        workout = setupCompletedWorkout(user, "Test Title1", time, "Workout Notes");
        workout2 = setupCompletedWorkout(user, "Test Title2", time, "Workout2 Notes");

        chestExercise = new Exercise(
                "Push Up",
                ExerciseGroup.CHEST,
                ExerciseType.UPPERBODY,
                "A basic push up");

        cardioExercise = new Exercise(
                "Run",
                ExerciseGroup.CARDIO,
                ExerciseType.CARDIO,
                "Run fast");

        cardioExerciseRecord = new ExerciseRecord(cardioExercise, user, time, workout, null, workout, null, workout,
                null, workout, null, workout, 120, workout, (float) 900, workout, (float) 3.58, workout);
        pushUpExerciseRecord = new ExerciseRecord(chestExercise, user, time, workout, 2, workout, 10, workout,
                (float) 15.00, workout, (float) 215, workout, null, workout, null, workout, null, workout);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWorkoutById() {
        long id = 1;
        when(workoutRepository.findById(id)).thenReturn(Optional.of(workout));

        Workout foundWorkout = workoutService.getWorkoutById(id);
        assertThat(foundWorkout.getTitle()).isEqualTo("Test Title1");
        assertThat(foundWorkout.getWorkoutNotes()).isEqualTo("Workout Notes");

        verify(workoutRepository, times(1)).findById(id);
    }

}
