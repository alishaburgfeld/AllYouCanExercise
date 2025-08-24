package com.allyoucanexercise.back_end.workout;

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
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetService;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserService;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseService;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;

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

class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private UserService userService;

    @Mock
    private ExerciseService exerciseService;

    @Mock
    private WorkoutExerciseService workoutExerciseService;

    @Mock
    private ExerciseSetService exerciseSetService;

    @InjectMocks
    private WorkoutService workoutService;

    private Workout workout;
    private Workout workout2;
    private User user;
    private final String fixedTimeString = "2025-04-02 10:30:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime time = LocalDateTime.parse(fixedTimeString, formatter);

    private Workout setupCompletedWorkout(User user, String title, LocalDateTime completedAt, String workoutNotes) {
        Workout temporaryWorkout = new Workout();
        temporaryWorkout.setUser(user);
        temporaryWorkout.setTitle(title);
        temporaryWorkout.setCompletedAt(completedAt);
        temporaryWorkout.setWorkoutNotes(workoutNotes);
        return temporaryWorkout;
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username1");
        user.setPassword("password1");

        workout = setupCompletedWorkout(user, "Test Title1", time, "Workout Notes");
        workout2 = setupCompletedWorkout(user, "Test Title2", time, "Workout2 Notes");
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

    @Test
    void testGetUserWorkouts() {

        when(workoutRepository.findByUser(user)).thenReturn(List.of(workout, workout2));
        when(userService.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        List<Workout> results = workoutService.getWorkoutsByUsername(user.getUsername());
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getTitle()).isEqualTo(workout.getTitle());
        assertThat(results.get(1).getTitle()).isEqualTo(workout2.getTitle());

        verify(workoutRepository).findByUser(user);
    }

    @Test
    void testSaveWorkout() {
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        Workout savedWorkout = workoutService.saveWorkout(workout);
        assertThat(savedWorkout.getTitle()).isEqualTo("Test Title1");

        verify(workoutRepository).save(workout);
    }

    @Test
    @DisplayName("test save works if notes are null")
    void testSaveWithNoNotes() {
        Workout workoutWithNoNotes = workout;
        workoutWithNoNotes.setWorkoutNotes(null);

        when(workoutRepository.save(workoutWithNoNotes)).thenReturn(workoutWithNoNotes);

        Workout savedWorkout = workoutService.saveWorkout(workoutWithNoNotes);
        assertThat(savedWorkout.getTitle()).isEqualTo("Test Title1");

        verify(workoutRepository).save(workoutWithNoNotes);
    }

    @Test
    @DisplayName("save fails if title is null")
    void testSaveFailsOnNullTitle() {
        Workout invalidWorkout = workout;
        invalidWorkout.setTitle(null);

        // this is the type of exception thrown when you don't follow @NotNull, etc
        when(workoutRepository.save(invalidWorkout))
                .thenThrow(new DataIntegrityViolationException("Title cannot be null"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            workoutService.saveWorkout(invalidWorkout);
        });

        verify(workoutRepository).save(invalidWorkout);
    }

    @Test
    @DisplayName("update fails if workout doesn't exist")
    void testUpdateFailsIfWorkoutDoesNotExist() {
        long id = 1;
        workout.setId(id);
        long invalidId = 2;

        when(workoutRepository.existsById(invalidId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> {
            workoutService.updateWorkout(workout, invalidId);
        });

        verify(workoutRepository).existsById(invalidId);
    }

    @Test
    void testUpdateWorkout() {
        long id = 1;

        workout.setId(id);
        Workout workoutWithNewTitle = workout;
        workoutWithNewTitle.setTitle("Updated Workout Title");

        when(workoutRepository.existsById(id)).thenReturn(true);
        when(workoutRepository.save(workoutWithNewTitle))
                .thenReturn(workoutWithNewTitle);

        Workout updatedWorkout = workoutService.updateWorkout(workoutWithNewTitle, workout.getId());
        assertThat(updatedWorkout.getTitle()).isEqualTo("Updated Workout Title");

        verify(workoutRepository).save(workoutWithNewTitle);
    }

    @Test
    void testDelete() {
        long id = 1;
        workout.setId(id);
        when(workoutRepository.existsById(id)).thenReturn(true);

        workoutService.deleteWorkout(id);
        verify(workoutRepository, times(1)).existsById(id);
        verify(workoutRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("delete fails if workout doesn't exist")
    void testDeleteFailsIfWorkoutDoesNotExist() {
        long id = 1;
        workout.setId(id);
        long invalidId = 2;

        when(workoutRepository.existsById(invalidId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> {
            workoutService.deleteWorkout(invalidId);
        });

        verify(workoutRepository).existsById(invalidId);
    }

    @Test
    void testSaveFullWorkout() {
        Float weight = (float) 10.00;
        long id = 1;
        long id2 = 2;

        WorkoutDetailsDTO workoutDetailsDTO = setupWorkoutDetailsDTO("username1", "Test Title1", time, "Workout Notes");

        ExerciseSetDTO set1 = setupExerciseSetDTO(10, weight, null, null, null, null);
        ExerciseSetDTO set2 = setupExerciseSetDTO(10, weight, null, null, null, null);
        ExerciseSetDTO set3 = setupExerciseSetDTO(null, null, 120, (float) 900, "meters", (float) 3.58);

        WorkoutExerciseDetailsDTO workoutExerciseDetails1 = setupWorkoutExerciseDetailsDTO(id, List.of(set1, set2));
        WorkoutExerciseDetailsDTO workoutExerciseDetails2 = setupWorkoutExerciseDetailsDTO((long) 2,
                List.of(set3));

        WorkoutRequestDTO workoutRequestDTO = new WorkoutRequestDTO();

        workoutRequestDTO.setWorkoutDetails(workoutDetailsDTO);
        workoutRequestDTO.setWorkoutExerciseDetails(List.of(workoutExerciseDetails1, workoutExerciseDetails2));

        Exercise chestExercise = new Exercise(
                "Push Up",
                ExerciseGroup.CHEST,
                ExerciseType.UPPERBODY,
                "A basic push up");

        Exercise cardioExercise = new Exercise(
                "Run",
                ExerciseGroup.CARDIO,
                ExerciseType.CARDIO,
                "Run fast");

        chestExercise.setId(id);
        cardioExercise.setId(id2);

        WorkoutExercise workoutExercise = new WorkoutExercise(workout, chestExercise, 1);
        WorkoutExercise workoutExercise2 = new WorkoutExercise(workout, cardioExercise, 2);

        when(userService.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(exerciseService.getExerciseById(id)).thenReturn(chestExercise);
        when(exerciseService.getExerciseById(id2)).thenReturn(cardioExercise);

        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        // Initially here I was passing in my workout not the any workout class version,
        // but my test was failing because I was passing one instance of Workout to
        // when(...).thenReturn(...) in my test, but inside
        // saveFullWorkout(), the method saves a different instance of Workout
        // Even if the fields are the same, unless the exact object reference matches,
        // Mockito will treat them as different objects.

        // eq(...) is an argument matcher that tells Mockito to match a specific exact
        // value — using .equals() under the hood
        // Mockito requires either all raw values, or all matchers. You can't mix them
        // freely. So when you use any(), you need to use eq() for the rest.

        when(workoutExerciseService.saveWorkoutExercise(any(Workout.class), eq(chestExercise), eq(1)))
                .thenReturn(workoutExercise);

        when(workoutExerciseService.saveWorkoutExercise(any(Workout.class), eq(cardioExercise), eq(2)))
                .thenReturn(workoutExercise2);

        ExerciseSet savedSet1 = new ExerciseSet(workoutExercise, 1, set1.getReps(), set1.getWeight(),
                set1.getDurationSeconds(),
                set1.getDistanceMeters(), set1.getDistanceMeasurement(), set1.getPacePerMile());

        ExerciseSet savedSet2 = new ExerciseSet(workoutExercise, 2, set2.getReps(), set2.getWeight(),
                set2.getDurationSeconds(),
                set2.getDistanceMeters(), set2.getDistanceMeasurement(), set2.getPacePerMile());

        ExerciseSet savedSet3 = new ExerciseSet(workoutExercise2, 1, set3.getReps(), set3.getWeight(),
                set3.getDurationSeconds(),
                set3.getDistanceMeters(), set3.getDistanceMeasurement(), set3.getPacePerMile());

        when(exerciseSetService.saveExerciseSet(workoutExercise, 1, set1.getReps(), set1.getWeight(),
                set1.getDurationSeconds(), set1.getDistanceMeters(), set1.getDistanceMeasurement(),
                set1.getPacePerMile()))
                .thenReturn(savedSet1);

        when(exerciseSetService.saveExerciseSet(workoutExercise, 2, set2.getReps(), set2.getWeight(),
                set2.getDurationSeconds(), set2.getDistanceMeters(), set2.getDistanceMeasurement(),
                set2.getPacePerMile()))
                .thenReturn(savedSet2);

        when(exerciseSetService.saveExerciseSet(workoutExercise2, 1, set3.getReps(), set3.getWeight(),
                set3.getDurationSeconds(), set3.getDistanceMeters(), set3.getDistanceMeasurement(),
                set3.getPacePerMile()))
                .thenReturn(savedSet3);

        workoutService.saveFullWorkout(workoutRequestDTO);

        verify(userService).getUserByUsername(user.getUsername());
        verify(exerciseService, times(1)).getExerciseById((long) 1);
        verify(exerciseService, times(1)).getExerciseById((long) 2);
        // verify(workoutService).saveWorkout(any(Workout.class));
        verify(workoutExerciseService, times(1)).saveWorkoutExercise(any(Workout.class), eq(chestExercise), eq(1));

        verify(workoutExerciseService, times(1)).saveWorkoutExercise(any(Workout.class), eq(cardioExercise), eq(2));

        verify(exerciseSetService, times(1)).saveExerciseSet(workoutExercise, 1, set1.getReps(), set1.getWeight(),
                set1.getDurationSeconds(), set1.getDistanceMeters(), set1.getDistanceMeasurement(),
                set1.getPacePerMile());

        verify(exerciseSetService, times(1)).saveExerciseSet(workoutExercise, 2, set2.getReps(), set2.getWeight(),
                set2.getDurationSeconds(), set2.getDistanceMeters(), set2.getDistanceMeasurement(),
                set2.getPacePerMile());

        verify(exerciseSetService, times(1)).saveExerciseSet(workoutExercise2, 1, set3.getReps(), set3.getWeight(),
                set3.getDurationSeconds(), set3.getDistanceMeters(), set3.getDistanceMeasurement(),
                set3.getPacePerMile());
    }

    private WorkoutDetailsDTO setupWorkoutDetailsDTO(String username, String title, LocalDateTime completedAt,
            String workoutNotes) {
        WorkoutDetailsDTO workoutDetails = new WorkoutDetailsDTO();
        workoutDetails.setUsername(username);
        workoutDetails.setTitle(title);
        workoutDetails.setCompletedAt(completedAt);
        workoutDetails.setWorkoutNotes(workoutNotes);
        return workoutDetails;
    }

    private WorkoutExerciseDetailsDTO setupWorkoutExerciseDetailsDTO(Long exerciseId, List<ExerciseSetDTO> sets) {
        WorkoutExerciseDetailsDTO workoutExerciseDetails = new WorkoutExerciseDetailsDTO();
        workoutExerciseDetails.setExerciseId(exerciseId);
        workoutExerciseDetails.setSets(sets);
        return workoutExerciseDetails;
    }

    private ExerciseSetDTO setupExerciseSetDTO(Integer reps, Float weight, Integer durationSeconds,
            Float distanceMeters, String distanceMeasurement, Float pacePerMile) {
        ExerciseSetDTO exerciseSetDTO = new ExerciseSetDTO();
        exerciseSetDTO.setReps(reps);
        exerciseSetDTO.setWeight(weight);
        exerciseSetDTO.setDurationSeconds(durationSeconds);
        exerciseSetDTO.setDistanceMeters(distanceMeters);
        exerciseSetDTO.setDistanceMeasurement(distanceMeasurement);
        exerciseSetDTO.setPacePerMile(pacePerMile);
        return exerciseSetDTO;
    }

}
