package com.allyoucanexercise.back_end.workout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserService;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private WorkoutService workoutService;

    private Workout workout;
    private Workout workout2;
    private User user;
    private final String fixedTimeString = "2025-04-02 10:30:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime time = LocalDateTime.parse(fixedTimeString, formatter);

    Workout setupCompletedWorkout(User user, String title, LocalDateTime completedAt, String workoutNotes) {
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
}
