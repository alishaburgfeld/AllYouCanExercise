package com.allyoucanexercise.back_end.setSegment;

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
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.workout.Workout;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SetSegmentServiceTest {
    @Mock
    private SetSegmentRepository setSegmentRepository;

    @InjectMocks
    private SetSegmentService setSegmentService;

    private Workout workout;
    private User user;
    private Exercise chestExercise;
    private WorkoutExercise workoutExercise;
    private ExerciseSet exerciseSet;
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
        exerciseSet = new ExerciseSet(workoutExercise, 1);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("save works on all weight workouts")
    void testSaveWeightSegment() {

        SetSegment setSegment = new SetSegment(exerciseSet, 1, 10, (float) 20, null, null, null, null);
        when(setSegmentRepository.save(setSegment)).thenReturn(setSegment);
        SetSegment result = setSegmentService.saveSetSegment(setSegment);

        assertEquals(setSegment, result);
        assertEquals(result.getExerciseSet(), exerciseSet);
        assertEquals(result.getSegmentOrder(), 1);
        assertEquals(result.getReps(), 10);
        assertEquals(result.getWeight(), (float) 20);
        verify(setSegmentRepository).save(setSegment);
    }

    @Test
    @DisplayName("save works on all cardio workouts with distance and time")
    void testSaveCardioSegment() {

        SetSegment setSegment = new SetSegment(exerciseSet, 1, null, null, 160, (float) 800,
                DistanceMeasurement.METERS,
                (float) 5.36);
        when(setSegmentRepository.save(setSegment)).thenReturn(setSegment);
        SetSegment result = setSegmentService.saveSetSegment(setSegment);

        assertEquals(setSegment, result);
        assertEquals(result.getExerciseSet(), exerciseSet);
        assertEquals(result.getSegmentOrder(), 1);
        assertEquals(result.getDurationSeconds(), 160);
        assertEquals(result.getDistanceMeters(), 800);
        assertEquals(result.getDistanceMeasurement(), DistanceMeasurement.METERS);
        assertEquals((float) result.getPacePerMile(), (float) 5.36);
        verify(setSegmentRepository).save(setSegment);
    }

    @Test
    @DisplayName("save fails if segment order is missing")
    void testSaveFailsOnNullSegmentOrder() {
        SetSegment invalidSetSegment = new SetSegment(exerciseSet, 1, null, null, 160, (float) 800,
                DistanceMeasurement.MILES,
                (float) 5.36);
        invalidSetSegment.setSegmentOrder(null);

        // this is the type of exception thrown when you don't follow @NotNull, etc
        when(setSegmentRepository.save(invalidSetSegment))
                .thenThrow(new DataIntegrityViolationException("SegmentOrder cannot be null"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            setSegmentService.saveSetSegment(invalidSetSegment);
        });

        verify(setSegmentRepository).save(invalidSetSegment);
    }

    @Test
    @DisplayName("save fails if exercise Set is missing")
    void testSaveFailsOnNullExerciseSet() {
        SetSegment invalidSetSegment = new SetSegment(exerciseSet, 1, null, null, 160, (float) 800,
                DistanceMeasurement.MILES,
                (float) 5.36);
        invalidSetSegment.setExerciseSet(null);

        when(setSegmentRepository.save(invalidSetSegment))
                .thenThrow(new DataIntegrityViolationException("ExerciseSet cannot be null"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            setSegmentService.saveSetSegment(invalidSetSegment);
        });

        verify(setSegmentRepository).save(invalidSetSegment);
    }
}
