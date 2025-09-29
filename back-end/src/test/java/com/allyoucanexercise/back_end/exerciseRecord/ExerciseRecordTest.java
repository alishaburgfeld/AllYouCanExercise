package com.allyoucanexercise.back_end.exerciseRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.workout.Workout;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;
import com.allyoucanexercise.back_end.exercise.ExerciseGroup;
import com.allyoucanexercise.back_end.exercise.ExerciseService;
import com.allyoucanexercise.back_end.exercise.ExerciseType;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;
import com.allyoucanexercise.back_end.setSegment.DistanceMeasurement;
import com.allyoucanexercise.back_end.setSegment.SetSegmentDTO;
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
    private Workout workout;
    private Workout workoutWithLaterTime;
    private User user;
    private ExerciseRecord cardioExerciseRecord;
    private ExerciseRecord pushUpExerciseRecord;
    private SetSegmentDTO set1Segment1DTO;
    private SetSegmentDTO set1Segment2DTO;
    private SetSegmentDTO set2Segment1DTO;
    private SetSegmentDTO cardioSetSegmentDTO;
    private ExerciseSetDTO set1DTO;
    private ExerciseSetDTO set2DTO;
    private ExerciseSetDTO cardioSetDTO;
    private WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO1;
    private WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO2;
    private final String fixedEarlyTimeString = "2025-04-02 10:30:00";
    private final String fixedLaterTimeString = "2025-06-13 13:30:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime earlierTime = LocalDateTime.parse(fixedEarlyTimeString, formatter);
    LocalDateTime latestTime = LocalDateTime.parse(fixedLaterTimeString, formatter);

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username1");
        user.setPassword("password1");

        workout = setupCompletedWorkout(user, "Test Title1", earlierTime, "Workout Notes");
        workoutWithLaterTime = setupCompletedWorkout(user, "Test Title2", latestTime, "Workout2 Notes");

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

        cardioExerciseRecord = new ExerciseRecord(cardioExercise, user, earlierTime, workout, null, workout, null,
                workout,
                null, workout, null, workout, 120, workout, (float) 900, workout, (float) 3.58, workout);
        pushUpExerciseRecord = new ExerciseRecord(chestExercise, user, earlierTime, workout, 2, workout, 11, workout,
                (float) 15.00, workout, (float) 230, workout, null, workout, null, workout, null, workout);

        set1Segment1DTO = setupSetSegmentDTO(7, (float) 10, null, null, null, null);
        set1Segment2DTO = setupSetSegmentDTO(4, (float) 15, null, null, null, null);
        set2Segment1DTO = setupSetSegmentDTO(10, (float) 10, null, null, null, null);
        cardioSetSegmentDTO = setupSetSegmentDTO(null, null, 120, (float) 900, DistanceMeasurement.METERS,
                (float) 3.58);

        set1DTO = setupExerciseSetDTO(List.of(set1Segment1DTO, set1Segment2DTO));
        set2DTO = setupExerciseSetDTO(List.of(set2Segment1DTO));

        cardioSetDTO = setupExerciseSetDTO(List.of(cardioSetSegmentDTO));

        workoutExerciseDetailsDTO1 = setupWorkoutExerciseDetailsDTO((long) 1,
                List.of(set1DTO, set2DTO));

        workoutExerciseDetailsDTO2 = setupWorkoutExerciseDetailsDTO((long) 2,
                List.of(cardioSetDTO));

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExerciseRecord() {
        when(exerciseRecordRepository.findByUserAndExercise(user, chestExercise))
                .thenReturn(Optional.of(pushUpExerciseRecord));

        ExerciseRecord exerciseRecord = exerciseRecordService.getExerciseRecord(user, chestExercise).orElse(null);
        assertThat(exerciseRecord.getMaxReps()).isEqualTo(11);
        assertThat(exerciseRecord.getMaxSets()).isEqualTo(2);
        assertThat(exerciseRecord.getMaxWeight()).isEqualTo(15);
        assertThat(exerciseRecord.getMaxVolume()).isEqualTo(215);
        assertThat(exerciseRecord.getMaxRepsWorkout()).isEqualTo(workout);
        assertThat(exerciseRecord.getMaxDistanceMeters()).isEqualTo(null);

        verify(exerciseRecordRepository, times(1)).findByUserAndExercise(user, chestExercise);
    }

    @Test
    void testGetExerciseRecordForCardio() {
        when(exerciseRecordRepository.findByUserAndExercise(user, cardioExercise))
                .thenReturn(Optional.of(cardioExerciseRecord));

        ExerciseRecord exerciseRecord = exerciseRecordService.getExerciseRecord(user, cardioExercise).orElse(null);
        assertThat(exerciseRecord.getMaxReps()).isEqualTo(null);
        assertThat(exerciseRecord.getMaxDurationSeconds()).isEqualTo(120);
        assertThat(exerciseRecord.getMaxDistanceMeters()).isEqualTo(900);
        assertThat(exerciseRecord.getMaxPacePerMile()).isEqualTo((float) 3.58);
        assertThat(exerciseRecord.getMaxDistanceWorkout()).isEqualTo(workout);

        verify(exerciseRecordRepository, times(1)).findByUserAndExercise(user, cardioExercise);
    }

    @Test
    void testSaveWhenNoExistingRecord() {
        when(exerciseRecordRepository.findByUserAndExercise(user, chestExercise)).thenReturn(Optional.empty());

        when(exerciseRecordRepository.save(any(ExerciseRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ExerciseRecord result = exerciseRecordService.saveExerciseRecord(workout, chestExercise,
                workoutExerciseDetailsDTO1, user);
        assertThat(result.getMaxSets()).isEqualTo(2);
        assertThat(result.getMaxReps()).isEqualTo(11);
        assertThat(result.getMaxWeight()).isEqualTo(15);
        assertThat(result.getLastExercised()).isEqualTo(earlierTime);
        assertThat(result.getMaxVolume()).isEqualTo((float) 230);
        assertThat(result.getMaxRepsWorkout()).isEqualTo(workout);
        assertThat(result.getMaxDistanceMeters()).isEqualTo((float) 0.0);
        assertThat(result.getMaxDurationSeconds()).isEqualTo(0);
        assertThat(result.getMaxPacePerMile()).isEqualTo((float) 0.0);

        verify(exerciseRecordRepository, times(1)).findByUserAndExercise(user, chestExercise);
        verify(exerciseRecordRepository, times(1)).save(any(ExerciseRecord.class));

    }

    @Test
    void testSaveWhenNoExistingRecordCardio() {
        when(exerciseRecordRepository.findByUserAndExercise(user, cardioExercise)).thenReturn(Optional.empty());

        when(exerciseRecordRepository.save(any(ExerciseRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ExerciseRecord result = exerciseRecordService.saveExerciseRecord(workout, cardioExercise,
                workoutExerciseDetailsDTO2, user);
        assertThat(result.getMaxSets()).isEqualTo(1);
        assertThat(result.getMaxReps()).isEqualTo(0);
        assertThat(result.getMaxWeight()).isEqualTo((float) 0);
        assertThat(result.getMaxDurationSeconds()).isEqualTo(120);
        assertThat(result.getMaxDistanceMeters()).isEqualTo((float) 900);
        assertThat(result.getMaxPacePerMile()).isEqualTo((float) 3.58);
        assertThat(result.getMaxDistanceWorkout()).isEqualTo(workout);

        verify(exerciseRecordRepository, times(1)).findByUserAndExercise(user, cardioExercise);
        verify(exerciseRecordRepository, times(1)).save(any(ExerciseRecord.class));

    }

    @Test
    void testLaterWorkoutCreatesARecord() {
        when(exerciseRecordRepository.findByUserAndExercise(user, chestExercise))
                .thenReturn(Optional.of(pushUpExerciseRecord));

        // this returns back whatever I save with it
        when(exerciseRecordRepository.save(any(ExerciseRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ExerciseRecord result = exerciseRecordService.saveExerciseRecord(workoutWithLaterTime, chestExercise,
                workoutExerciseDetailsDTO1, user);
        assertThat(result.getMaxSets()).isEqualTo(2);
        assertThat(result.getLastExercised()).isEqualTo(latestTime);
        assertThat(result.getLastExercisedWorkout()).isEqualTo(workoutWithLaterTime);

        verify(exerciseRecordRepository, times(1)).findByUserAndExercise(user, chestExercise);
        verify(exerciseRecordRepository, times(1)).save(any(ExerciseRecord.class));

    }

    private Workout setupCompletedWorkout(User user, String title, LocalDateTime completedAt, String workoutNotes) {
        Workout temporaryWorkout = new Workout(user, title, completedAt, workoutNotes);
        return temporaryWorkout;
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

    private WorkoutExerciseDetailsDTO setupWorkoutExerciseDetailsDTO(Long exerciseId, List<ExerciseSetDTO> sets) {
        WorkoutExerciseDetailsDTO workoutExerciseDetails = new WorkoutExerciseDetailsDTO();
        workoutExerciseDetails.setExerciseId(exerciseId);
        workoutExerciseDetails.setSets(sets);
        return workoutExerciseDetails;
    }

}
