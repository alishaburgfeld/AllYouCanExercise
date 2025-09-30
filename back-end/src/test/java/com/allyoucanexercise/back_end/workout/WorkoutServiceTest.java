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
import com.allyoucanexercise.back_end.exerciseRecord.ExerciseRecord;
import com.allyoucanexercise.back_end.exerciseRecord.ExerciseRecordService;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;
import com.allyoucanexercise.back_end.setSegment.SetSegment;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetService;
import com.allyoucanexercise.back_end.setSegment.DistanceMeasurement;
import com.allyoucanexercise.back_end.setSegment.SetSegmentDTO;
import com.allyoucanexercise.back_end.setSegment.SetSegmentService;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserService;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseService;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        @Mock
        private SetSegmentService setSegmentService;

        @Mock
        private ExerciseRecordService exerciseRecordService;

        @InjectMocks
        private WorkoutService workoutService;

        private Workout workout;
        private Workout workout2;
        private Exercise chestExercise;
        private Exercise cardioExercise;
        private User user;
        private ExerciseRecord cardioExerciseRecord;
        private ExerciseRecord pushUpExerciseRecord;
        private WorkoutDetailsDTO workoutDetailsDTO;
        private SetSegmentDTO set1Segment1DTO;
        private SetSegmentDTO set1Segment2DTO;
        private SetSegmentDTO set2Segment1DTO;
        private SetSegmentDTO cardioSetSegmentDTO;
        private ExerciseSetDTO set1DTO;
        private ExerciseSetDTO set2DTO;
        private ExerciseSetDTO cardioSetDTO;
        private WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO1;
        private WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO2;

        private WorkoutExercise chestWorkoutExercise;
        private WorkoutExercise cardioWorkoutExercise;

        private ExerciseSet chestExerciseSet1;
        private ExerciseSet chestExerciseSet2;
        private ExerciseSet cardioExerciseSet;

        private SetSegment chestExerciseSet1Segment1;

        private SetSegment chestExerciseSet1Segment2;

        private SetSegment chestExerciseSet2Segment1;

        private SetSegment savedCardioSegment;
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

                cardioExerciseRecord = new ExerciseRecord(cardioExercise, user, time, workout, 1, workout, 0,
                                workout,
                                (float) 0, workout, (float) 0, workout, 120, workout, (float) 900, workout,
                                (float) 3.58,
                                workout);
                pushUpExerciseRecord = new ExerciseRecord(chestExercise, user, time, workout, 2, workout, 10, workout,
                                (float) 15.00, workout, (float) 215, workout, 0, workout, (float) 0, workout, (float) 0,
                                workout);

                workoutDetailsDTO = setupWorkoutDetailsDTO("username1", "Test Title1", time, "Workout Notes");

                set1Segment1DTO = setupSetSegmentDTO(7, (float) 10.00, 0, (float) 0, null, (float) 0);
                set1Segment2DTO = setupSetSegmentDTO(3, (float) 15, 0, (float) 0, null, (float) 0);
                set2Segment1DTO = setupSetSegmentDTO(10, (float) 10.00, 0, (float) 0, null, (float) 0);
                cardioSetSegmentDTO = setupSetSegmentDTO(0, (float) 0, 120, (float) 900, DistanceMeasurement.METERS,
                                (float) 3.58);

                set1DTO = setupExerciseSetDTO(List.of(set1Segment1DTO, set1Segment2DTO));
                set2DTO = setupExerciseSetDTO(List.of(set2Segment1DTO));
                cardioSetDTO = setupExerciseSetDTO(List.of(cardioSetSegmentDTO));

                workoutExerciseDetailsDTO1 = setupWorkoutExerciseDetailsDTO(1L, "Push Up", ExerciseGroup.CHEST,
                                List.of(set1DTO, set2DTO));
                workoutExerciseDetailsDTO2 = setupWorkoutExerciseDetailsDTO(2L, "Run", ExerciseGroup.CARDIO,
                                List.of(cardioSetDTO));

                chestWorkoutExercise = new WorkoutExercise(workout, chestExercise, 1);
                cardioWorkoutExercise = new WorkoutExercise(workout, cardioExercise, 2);

                chestExerciseSet1 = new ExerciseSet(chestWorkoutExercise, 1);
                chestExerciseSet2 = new ExerciseSet(chestWorkoutExercise, 2);
                cardioExerciseSet = new ExerciseSet(cardioWorkoutExercise, 1);

                chestExerciseSet1Segment1 = new SetSegment(chestExerciseSet1, 1,
                                set1Segment1DTO.getReps(),
                                set1Segment1DTO.getWeight(),
                                set1Segment1DTO.getDurationSeconds(),
                                set1Segment1DTO.getDistanceMeters(), set1Segment1DTO.getDistanceMeasurement(),
                                set1Segment1DTO.getPacePerMile());

                chestExerciseSet1Segment2 = new SetSegment(chestExerciseSet1, 2, set1Segment2DTO.getReps(),
                                set1Segment2DTO.getWeight(),
                                set1Segment2DTO.getDurationSeconds(),
                                set1Segment2DTO.getDistanceMeters(), set1Segment2DTO.getDistanceMeasurement(),
                                set1Segment2DTO.getPacePerMile());

                chestExerciseSet2Segment1 = new SetSegment(chestExerciseSet2, 1, set2Segment1DTO.getReps(),
                                set2Segment1DTO.getWeight(),
                                set2Segment1DTO.getDurationSeconds(),
                                set2Segment1DTO.getDistanceMeters(), set2Segment1DTO.getDistanceMeasurement(),
                                set2Segment1DTO.getPacePerMile());

                savedCardioSegment = new SetSegment(cardioExerciseSet, 1, cardioSetSegmentDTO.getReps(),
                                cardioSetSegmentDTO.getWeight(),
                                cardioSetSegmentDTO.getDurationSeconds(),
                                cardioSetSegmentDTO.getDistanceMeters(), cardioSetSegmentDTO.getDistanceMeasurement(),
                                cardioSetSegmentDTO.getPacePerMile());

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

        // @Test
        // void testGetUserWorkouts() {

        // when(workoutRepository.findByUser(user)).thenReturn(List.of(workout,
        // workout2));
        // when(userService.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // List<Workout> results =
        // workoutService.getWorkoutsByUsername(user.getUsername());
        // assertThat(results).hasSize(2);
        // assertThat(results.get(0).getTitle()).isEqualTo(workout.getTitle());
        // assertThat(results.get(1).getTitle()).isEqualTo(workout2.getTitle());

        // verify(workoutRepository).findByUser(user);
        // }

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
                long id = 1;
                long id2 = 2;

                WorkoutRequestDTO workoutRequestDTO = new WorkoutRequestDTO();

                workoutRequestDTO.setWorkoutDetails(workoutDetailsDTO);
                workoutRequestDTO.setWorkoutExerciseDetails(
                                List.of(workoutExerciseDetailsDTO1, workoutExerciseDetailsDTO2));

                chestExercise.setId(id);
                cardioExercise.setId(id2);

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
                                .thenReturn(chestWorkoutExercise);

                when(workoutExerciseService.saveWorkoutExercise(any(Workout.class), eq(cardioExercise), eq(2)))
                                .thenReturn(cardioWorkoutExercise);

                when(exerciseSetService.saveExerciseSet(chestWorkoutExercise, 1)).thenReturn(chestExerciseSet1);
                when(exerciseSetService.saveExerciseSet(chestWorkoutExercise, 2)).thenReturn(chestExerciseSet2);
                when(exerciseSetService.saveExerciseSet(cardioWorkoutExercise, 1)).thenReturn(cardioExerciseSet);

                when(setSegmentService.saveSetSegment(chestExerciseSet1, 1, set1Segment1DTO.getReps(),
                                set1Segment1DTO.getWeight(),
                                set1Segment1DTO.getDurationSeconds(), set1Segment1DTO.getDistanceMeters(),
                                set1Segment1DTO.getDistanceMeasurement(),
                                set1Segment1DTO.getPacePerMile()))
                                .thenReturn(chestExerciseSet1Segment1);

                when(setSegmentService.saveSetSegment(chestExerciseSet1, 2, set1Segment2DTO.getReps(),
                                set1Segment2DTO.getWeight(),
                                set1Segment2DTO.getDurationSeconds(), set1Segment2DTO.getDistanceMeters(),
                                set1Segment2DTO.getDistanceMeasurement(),
                                set1Segment2DTO.getPacePerMile()))
                                .thenReturn(chestExerciseSet1Segment2);

                when(setSegmentService.saveSetSegment(chestExerciseSet2, 1, set2Segment1DTO.getReps(),
                                set2Segment1DTO.getWeight(),
                                set2Segment1DTO.getDurationSeconds(), set2Segment1DTO.getDistanceMeters(),
                                set2Segment1DTO.getDistanceMeasurement(),
                                set2Segment1DTO.getPacePerMile()))
                                .thenReturn(chestExerciseSet2Segment1);

                when(setSegmentService.saveSetSegment(cardioExerciseSet, 1, cardioSetSegmentDTO.getReps(),
                                cardioSetSegmentDTO.getWeight(),
                                cardioSetSegmentDTO.getDurationSeconds(), cardioSetSegmentDTO.getDistanceMeters(),
                                cardioSetSegmentDTO.getDistanceMeasurement(),
                                cardioSetSegmentDTO.getPacePerMile()))
                                .thenReturn(savedCardioSegment);

                // this is a dummy exercise record - the record would actually ahve different
                // values, but I need a different test
                // for that
                when(exerciseRecordService.saveExerciseRecord(any(ExerciseRecord.class)))
                                .thenReturn(pushUpExerciseRecord);

                workoutService.saveFullWorkout(workoutRequestDTO);

                verify(userService).getUserByUsername(user.getUsername());
                // also called when doing exercise Record
                verify(exerciseService, times(2)).getExerciseById((long) 1);
                verify(exerciseService, times(2)).getExerciseById((long) 2);
                // verify(workoutService).saveWorkout(any(Workout.class));
                verify(workoutExerciseService, times(1)).saveWorkoutExercise(any(Workout.class), eq(chestExercise),
                                eq(1));

                verify(workoutExerciseService, times(1)).saveWorkoutExercise(any(Workout.class), eq(cardioExercise),
                                eq(2));

                verify(exerciseSetService, times(1)).saveExerciseSet(chestWorkoutExercise, 1);
                verify(exerciseSetService, times(1)).saveExerciseSet(chestWorkoutExercise, 2);
                verify(exerciseSetService, times(1)).saveExerciseSet(cardioWorkoutExercise, 1);

                verify(setSegmentService, times(1)).saveSetSegment(chestExerciseSet1, 1, set1Segment1DTO.getReps(),
                                set1Segment1DTO.getWeight(),
                                set1Segment1DTO.getDurationSeconds(), set1Segment1DTO.getDistanceMeters(),
                                set1Segment1DTO.getDistanceMeasurement(),
                                set1Segment1DTO.getPacePerMile());

                verify(setSegmentService, times(1)).saveSetSegment(chestExerciseSet1, 2, set1Segment2DTO.getReps(),
                                set1Segment2DTO.getWeight(),
                                set1Segment2DTO.getDurationSeconds(), set1Segment2DTO.getDistanceMeters(),
                                set1Segment2DTO.getDistanceMeasurement(),
                                set1Segment2DTO.getPacePerMile());

                verify(setSegmentService, times(1)).saveSetSegment(chestExerciseSet2, 1, set2Segment1DTO.getReps(),
                                set2Segment1DTO.getWeight(),
                                set2Segment1DTO.getDurationSeconds(), set2Segment1DTO.getDistanceMeters(),
                                set2Segment1DTO.getDistanceMeasurement(),
                                set2Segment1DTO.getPacePerMile());

                verify(setSegmentService, times(1)).saveSetSegment(cardioExerciseSet, 1, cardioSetSegmentDTO.getReps(),
                                cardioSetSegmentDTO.getWeight(),
                                cardioSetSegmentDTO.getDurationSeconds(), cardioSetSegmentDTO.getDistanceMeters(),
                                cardioSetSegmentDTO.getDistanceMeasurement(),
                                cardioSetSegmentDTO.getPacePerMile());

        }

        @Test
        void testViewFullWorkout() {
                chestExercise.setId(1L);
                cardioExercise.setId(2L);

                // workout = setupCompletedWorkout(user, "Test Title1", time, "Workout Notes");
                when(workoutRepository.findById((long) 1)).thenReturn(Optional.of(workout));
                // allWorkoutExercises
                when(workoutExerciseService.getAllWorkoutExercisesByWorkout(workout))
                                .thenReturn(List.of(chestWorkoutExercise, cardioWorkoutExercise));

                // exerciseSets
                when(exerciseSetService.getAllExerciseSetsByWorkoutExercise(chestWorkoutExercise))
                                .thenReturn(List.of(chestExerciseSet1, chestExerciseSet2));

                when(exerciseSetService.getAllExerciseSetsByWorkoutExercise(cardioWorkoutExercise))
                                .thenReturn(List.of(cardioExerciseSet));

                // segments

                when(setSegmentService.getAllSetSegmentsByExerciseSet(chestExerciseSet1))
                                .thenReturn(List.of(chestExerciseSet1Segment1, chestExerciseSet1Segment2));
                when(setSegmentService.getAllSetSegmentsByExerciseSet(chestExerciseSet2))
                                .thenReturn(List.of(chestExerciseSet2Segment1));
                when(setSegmentService.getAllSetSegmentsByExerciseSet(cardioExerciseSet))
                                .thenReturn(List.of(savedCardioSegment));

                WorkoutResponseDTO workoutResponseDTO = new WorkoutResponseDTO();
                List<WorkoutExerciseDetailsDTO> allWorkoutExerciseDetails = List.of(workoutExerciseDetailsDTO1,
                                workoutExerciseDetailsDTO2);
                workoutResponseDTO.setWorkoutDetails(workoutDetailsDTO);
                workoutResponseDTO.setWorkoutExerciseDetails(allWorkoutExerciseDetails);

                // when(workoutService.getFullWorkoutAndExerciseDetailsById((long)
                // 1)).thenReturn();

                WorkoutResponseDTO result = workoutService.getFullWorkoutAndExerciseDetailsById((long) 1);

                assertThat(result.getWorkoutDetails())
                                .usingRecursiveComparison()
                                .isEqualTo(workoutDetailsDTO);

                assertThat(result.getWorkoutExerciseDetails())
                                .usingRecursiveComparison()
                                .isEqualTo(allWorkoutExerciseDetails);

                verify(workoutRepository, times(1)).findById((long) 1);
                verify(workoutExerciseService, times(1)).getAllWorkoutExercisesByWorkout(workout);
                verify(exerciseSetService, times(1)).getAllExerciseSetsByWorkoutExercise(chestWorkoutExercise);
                verify(exerciseSetService, times(1)).getAllExerciseSetsByWorkoutExercise(cardioWorkoutExercise);
                verify(setSegmentService, times(1)).getAllSetSegmentsByExerciseSet(chestExerciseSet1);
                verify(setSegmentService, times(1)).getAllSetSegmentsByExerciseSet(chestExerciseSet2);
                verify(setSegmentService, times(1)).getAllSetSegmentsByExerciseSet(cardioExerciseSet);

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

        private WorkoutExerciseDetailsDTO setupWorkoutExerciseDetailsDTO(Long exerciseId, String exerciseName,
                        ExerciseGroup exerciseGroup, List<ExerciseSetDTO> sets) {
                WorkoutExerciseDetailsDTO workoutExerciseDetails = new WorkoutExerciseDetailsDTO();
                workoutExerciseDetails.setExerciseId(exerciseId);
                workoutExerciseDetails.setExerciseName(exerciseName);
                workoutExerciseDetails.setExerciseGroup(exerciseGroup);
                workoutExerciseDetails.setSets(sets);
                return workoutExerciseDetails;
        }

        private ExerciseSetDTO setupExerciseSetDTO(List<SetSegmentDTO> segmentDTOs) {
                ExerciseSetDTO exerciseSetDTO = new ExerciseSetDTO();
                exerciseSetDTO.setSegments(segmentDTOs);
                return exerciseSetDTO;
        }

        private SetSegmentDTO setupSetSegmentDTO(Integer reps, Float weight, Integer durationSeconds,
                        Float distanceMeters, DistanceMeasurement distanceMeasurement, Float pacePerMile) {
                SetSegmentDTO setSegmentDTO = new SetSegmentDTO();
                setSegmentDTO.setReps(reps);
                setSegmentDTO.setWeight(weight);
                setSegmentDTO.setDurationSeconds(durationSeconds);
                setSegmentDTO.setDistanceMeters(distanceMeters);
                setSegmentDTO.setDistanceMeasurement(distanceMeasurement);
                setSegmentDTO.setPacePerMile(pacePerMile);
                return setSegmentDTO;
        }

}

// may need in the future for viewing a workout:
// WorkoutResponseDTO dto = new WorkoutResponseDTO();
// WorkoutDetailsDTO wddto = new WorkoutDetailsDTO();
// wddto.setCompletedAt(LocalDateTime.now());
// wddto.setTitle("test title");
// wddto.setUsername("alb");
// wddto.setWorkoutNotes("test notes");

// dto.setWorkoutDetails(wddto);
// WorkoutExerciseDetailsDTO wedto1 = new WorkoutExerciseDetailsDTO();
// ExerciseSetDTO esdto1 = new ExerciseSetDTO();
// esdto1.setReps(10);
// esdto1.setWeight(20f);

// List<ExerciseSetDTO> exerciseSetDTOs = List.of(esdto1);
// List<WorkoutExerciseDetailsDTO> workoutExerciseDetailDTOs = List.of(wedto1);
// wedto1.setExerciseId(1l);
// wedto1.setSets(exerciseSetDTOs);
// dto.setWorkoutExerciseDetails(workoutExerciseDetailDTOs);
// return dto;