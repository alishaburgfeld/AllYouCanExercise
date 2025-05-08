package com.allyoucanexercise.back_end.exerciseSet;

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
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ExerciseSetServiceTest {

    @Mock
    private ExerciseSetRepository exerciseSetRepository;

    @InjectMocks
    private ExerciseSetService exerciseSetService;

    private Workout workout;
    private User user;
    private Exercise chestExercise;
    private WorkoutExercise workoutExercise;
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
        workoutExercise = new WorkoutExercise(workout, chestExercise, 1);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("save works on all weight workouts")
    void testSave() {

        ExerciseSet exerciseSet = new ExerciseSet(workoutExercise, 1, 10, (float) 20, null, null);
        when(exerciseSetRepository.save(exerciseSet)).thenReturn(exerciseSet);
        ExerciseSet result = exerciseSetService.saveExerciseSet(exerciseSet);

        assertEquals(exerciseSet, result);
        assertEquals(result.getWorkoutExercise(), workoutExercise);
        assertEquals(result.getSetOrder(), 1);
        assertEquals(result.getReps(), 10);
        assertEquals(result.getWeight(), (float) 20);
        verify(exerciseSetRepository).save(exerciseSet);
    }

    @Test
    @DisplayName("save works on all cardio workouts with distance and time")
    void testSaveCardioExercise() {

        ExerciseSet exerciseSet = new ExerciseSet(workoutExercise, 1, null, null, 900, (float) 300);
        when(exerciseSetRepository.save(exerciseSet)).thenReturn(exerciseSet);
        ExerciseSet result = exerciseSetService.saveExerciseSet(exerciseSet);

        assertEquals(exerciseSet, result);
        assertEquals(result.getWorkoutExercise(), workoutExercise);
        assertEquals(result.getSetOrder(), 1);
        assertEquals(result.getDurationSeconds(), 900);
        assertEquals(result.getDistanceMeters(), 300);
        verify(exerciseSetRepository).save(exerciseSet);
    }

    @Test
    @DisplayName("save fails if set order is missing")
    void testSaveFailsOnNullSetOrder() {
        ExerciseSet invalidExerciseSet = new ExerciseSet(workoutExercise, 1, null, null, 900, (float) 300);
        invalidExerciseSet.setSetOrder(null);

        // this is the type of exception thrown when you don't follow @NotNull, etc
        when(exerciseSetRepository.save(invalidExerciseSet))
                .thenThrow(new DataIntegrityViolationException("SetOrder cannot be null"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            exerciseSetService.saveExerciseSet(invalidExerciseSet);
        });

        verify(exerciseSetRepository).save(invalidExerciseSet);
    }
}