package com.allyoucanexercise.back_end.workoutExercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.exercise.ExerciseGroup;
import com.allyoucanexercise.back_end.exercise.ExerciseType;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.workout.Workout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WorkoutExerciseServiceTest {

    @Mock
    private WorkoutExerciseRepository workoutExerciseRepository;

    @InjectMocks
    private WorkoutExerciseService workoutExerciseService;

    private Workout workout;
    private User user;
    private Exercise chestExercise;
    private final String fixedTimeString = "2025-04-02 10:30:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime time = LocalDateTime.parse(fixedTimeString, formatter);

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username1");
        user.setPassword("password1");
        chestExercise = new Exercise(
                "Push Up",
                ExerciseGroup.CHEST,
                ExerciseType.UPPERBODY,
                "A basic push up");
        workout = new Workout(user, "Test Title1", time, "Workout Notes");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {

        WorkoutExercise workoutExercise = new WorkoutExercise(workout, chestExercise, 1);
        when(workoutExerciseRepository.save(workoutExercise)).thenReturn(workoutExercise);
        WorkoutExercise result = workoutExerciseService.saveWorkoutExercise(workoutExercise);

        // (result.getWorkout())
        assertEquals(workoutExercise, result);
        assertEquals(workoutExercise.getExerciseOrder(), 1);
        verify(workoutExerciseRepository).save(workoutExercise);
    }

    @Test
    @DisplayName("save fails if exercise order is missing")
    void testSaveFailsOnNullExerciseOrder() {
        WorkoutExercise invalidWorkoutExercise = new WorkoutExercise(workout, chestExercise, 1);
        invalidWorkoutExercise.setExerciseOrder(null);

        // this is the type of exception thrown when you don't follow @NotNull, etc
        when(workoutExerciseRepository.save(invalidWorkoutExercise))
                .thenThrow(new DataIntegrityViolationException("ExerciseOrder cannot be null"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            workoutExerciseService.saveWorkoutExercise(invalidWorkoutExercise);
        });

        verify(workoutExerciseRepository).save(invalidWorkoutExercise);
    }

}
