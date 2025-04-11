package com.allyoucanexercise.back_end.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    private Exercise chestExercise;
    private Exercise cardioExercise1;
    private Exercise cardioExercise2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        chestExercise = new Exercise(
                "Push Up",
                ExerciseGroup.CHEST,
                ExerciseType.UPPERBODY,
                "A basic push up");

        cardioExercise1 = new Exercise(
                "Run",
                ExerciseGroup.CARDIO,
                ExerciseType.CARDIO,
                "Run fast");

        cardioExercise2 = new Exercise(
                "Jump Rope",
                ExerciseGroup.CARDIO,
                ExerciseType.CARDIO,
                "Jump over the rope");
    }

    @Test
    void testGetExerciseById() {
        long id = 1;
        when(exerciseRepository.findById(id)).thenReturn(Optional.of(exercise));

        Exercise foundExercise = exerciseService.getExerciseById(id);
        assertThat(foundExercise.getName()).isEqualTo("Push Up");

        verify(exerciseRepository, times(1)).findById(id);
    }

    @Test
    void testGetExercisesByType() {
        when(exerciseRepository.findAllByExerciseType(ExerciseType.UPPERBODY))
                .thenReturn(List.of(chestExercise));

        List<Exercise> results = exerciseService.getExercisesByType(ExerciseType.UPPERBODY);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getExerciseType()).isEqualTo(ExerciseType.UPPERBODY);

        verify(exerciseRepository).findAllByExerciseType(ExerciseType.UPPERBODY);
    }

    @Test
    void testGetExercisesByTypeCardio() {
        when(exerciseRepository.findAllByExerciseType(ExerciseType.CARDIO))
                .thenReturn(List.of(cardioExercise1, cardioExercise2));

        List<Exercise> results = exerciseService.getExercisesByType(ExerciseType.CARDIO);
        assertThat(results).hasSize(2);
        assertThat(results.get(1).getExerciseType()).isEqualTo(ExerciseType.CARDIO);
        assertThat(results.get(0).getName()).isEqualTo("Run");
        assertThat(results.get(1).getName()).isEqualTo("Jump Rope");

        verify(exerciseRepository).findAllByExerciseType(ExerciseType.CARDIO);
    }

    @Test
    void testGetExercisesByGroup() {
        when(exerciseRepository.findAllByExerciseGroup(ExerciseGroup.CHEST))
                .thenReturn(List.of(chestExercise));

        List<Exercise> results = exerciseService.getExercisesByGroup(ExerciseGroup.CHEST);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getExerciseGroup()).isEqualTo(ExerciseGroup.CHEST);

        verify(exerciseRepository).findAllByExerciseGroup(ExerciseGroup.CHEST);
    }

    @Test
    void testSaveExercise() {
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(chestExercise);

        Exercise savedExercise = exerciseService.saveExercise(chestExercise);
        assertThat(savedExercise.getName()).isEqualTo("Push Up");

        verify(exerciseRepository).save(chestExercise);
    }

    @Test
    @DisplayName("save fails if name is null")
    void testSaveFailsOnNullName() {
        Exercise invalidExercise = chestExercise;
        invalidExercise.setName(null);

        // this is the type of exception thrown when you don't follow @NotNull, etc
        when(exerciseRepository.save(invalidExercise))
                .thenThrow(new DataIntegrityViolationException("Name cannot be null"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            exerciseService.saveExercise(invalidExercise);
        });

        verify(exerciseRepository).save(invalidExercise);
    }

    @Test
    @DisplayName("update fails if exercise doesn't exist")
    void testUpdateFailsIfExerciseDoesNotExist() {
        long id = 1;
        cardioExercise1.setId(id);
        long invalidId = 2;

        when(exerciseRepository.existsById(invalidId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> {
            exerciseService.updateExercise(cardioExercise1, invalidId);
        });

        verify(exerciseRepository).existsById(invalidId);
    }

    @Test
    void testUpdateExercise() {
        long id = 1;

        Exercise exerciseWithNewDescription = cardioExercise1;
        cardioExercise1.setId(id);
        exerciseWithNewDescription.setDescription("Updated Run description");

        when(exerciseRepository.update(exerciseWithNewDescription, cardioExercise1.getId()))
                .thenReturn(exerciseWithNewDescription);

        Exercise updatedExercise = exerciseService.updateExercise(exerciseWithNewDescription, cardioExercise1.getId());
        assertThat(updatedExercise.getDescription()).isEqualTo("Updated Run description");

        verify(exerciseRepository).update(exerciseWithNewDescription, id);
    }

    @Test
    void testDelete() {
        long id = 1;
        chestExercise.setId(id);
        exerciseService.deleteExercise(id);
        verify(exerciseRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("delete fails if exercise doesn't exist")
    void testDeleteFailsIfExerciseDoesNotExist() {
        long id = 1;
        cardioExercise1.setId(id);
        long invalidId = 2;

        when(exerciseRepository.existsById(invalidId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> {
            exerciseService.deleteExercise(invalidId);
        });

        verify(exerciseRepository).existsById(invalidId);
    }
}
