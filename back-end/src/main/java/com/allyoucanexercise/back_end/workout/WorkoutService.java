package com.allyoucanexercise.back_end.workout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

// import com.allyoucanexercise.back_end.workout.WorkoutRepository;

import jakarta.persistence.EntityNotFoundException;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.user.UserService;
import com.allyoucanexercise.back_end.exercise.ExerciseService;
import com.allyoucanexercise.back_end.exerciseRecord.ExerciseRecord;
import com.allyoucanexercise.back_end.exerciseRecord.ExerciseRecordService;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetService;
import com.allyoucanexercise.back_end.SetSegment.SetSegmentService;
import com.allyoucanexercise.back_end.SetSegment.DistanceMeasurement;
import com.allyoucanexercise.back_end.SetSegment.SetSegment;
import com.allyoucanexercise.back_end.SetSegment.SetSegmentDTO;
import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;
import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseService;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserService userService;
    private final WorkoutExerciseService workoutExerciseService;
    private final ExerciseService exerciseService;
    private final ExerciseRecordService exerciseRecordService;
    private final ExerciseSetService exerciseSetService;
    private final SetSegmentService setSegmentService;
    private static final Logger log = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(WorkoutRepository workoutRepository, UserService userService,
            WorkoutExerciseService workoutExerciseService, ExerciseService exerciseService,
            ExerciseSetService exerciseSetService, ExerciseRecordService exerciseRecordService,
            SetSegmentService setSegmentService) {
        this.workoutRepository = workoutRepository;
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.exerciseRecordService = exerciseRecordService;
        this.exerciseSetService = exerciseSetService;
        this.workoutExerciseService = workoutExerciseService;
        this.setSegmentService = setSegmentService;
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));
    }

    // public WorkoutDetailsDTO getFullWorkoutAndExerciseDetailsById(Long id) {
    public WorkoutResponseDTO getFullWorkoutAndExerciseDetailsById(Long id) {

        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

        WorkoutDetailsDTO workoutDetailsDTO = addValuesToWorkoutDetailsDTO(workout);

        List<WorkoutExerciseDetailsDTO> allWorkoutExerciseDetails = new ArrayList<>();
        List<WorkoutExercise> allWorkoutExercises = workoutExerciseService.getAllWorkoutExercisesByWorkout(workout);
        for (WorkoutExercise workoutExercise : allWorkoutExercises) {
            WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO = new WorkoutExerciseDetailsDTO();
            List<ExerciseSet> exerciseSets = exerciseSetService.getAllExerciseSetsByWorkoutExercise(workoutExercise);
            List<ExerciseSetDTO> exerciseSetDTOs = new ArrayList<>();
            for (ExerciseSet exerciseSet : exerciseSets) {
                ExerciseSetDTO exerciseSetDTODetails = addValuesToExerciseSetDTO(exerciseSet);
                exerciseSetDTOs.add(exerciseSetDTODetails);
            }
            System.err.println("workoutExercise line 81 is" + workoutExercise);
            System.err.println("workoutExercise line 81 is" + workoutExercise.getExercise());
            workoutExerciseDetailsDTO.setExerciseId(workoutExercise.getExercise().getId());
            workoutExerciseDetailsDTO.setExerciseName(workoutExercise.getExercise().getName());
            workoutExerciseDetailsDTO.setExerciseGroup(workoutExercise.getExercise().getExerciseGroup());
            workoutExerciseDetailsDTO.setSets(exerciseSetDTOs);
            allWorkoutExerciseDetails.add(workoutExerciseDetailsDTO);
        }
        WorkoutResponseDTO workoutResponseDTO = new WorkoutResponseDTO();
        workoutResponseDTO.setWorkoutDetails(workoutDetailsDTO);
        workoutResponseDTO.setWorkoutExerciseDetails(allWorkoutExerciseDetails);
        // log.error("*****", workoutResponseDTO);
        return workoutResponseDTO;
        // return workoutDetailsDTO;
    }

    @Transactional
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Transactional
    public Workout updateWorkout(Workout workout, Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new EntityNotFoundException("Workout with id " + id + " not found");
        }

        workout.setId(id);
        return workoutRepository.save(workout);
    }

    @Transactional
    public void deleteWorkout(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new EntityNotFoundException("Workout with id " + id + " not found");
        }
        workoutRepository.deleteById(id);
    }

    public List<WorkoutResponseDTO> getWorkoutsByUsername(String username) {
        // what exception does this throw?
        User user = userService.getUserByUsername(username).orElse(null);
        List<Workout> workouts = workoutRepository.findByUser(user);
        List<WorkoutResponseDTO> allUserWorkouts = new ArrayList<>();
        for (Workout workout : workouts) {
            WorkoutResponseDTO workoutResponseDTO = this.getFullWorkoutAndExerciseDetailsById(workout.getId());
            allUserWorkouts.add(workoutResponseDTO);
        }
        return allUserWorkouts;
    }

    @Transactional
    public Workout saveWorkout(User user, String title, LocalDateTime completedAt, String workoutNotes) {
        Workout workout = new Workout();
        workout.setUser(user);
        workout.setTitle(title);
        workout.setCompletedAt(completedAt);
        workout.setCreatedAt(LocalDateTime.now());
        workout.setWorkoutNotes(workoutNotes);
        return workoutRepository.save(workout);
    }

    @Transactional
    public void saveFullWorkout(WorkoutRequestDTO workoutRequestDTO) {
        // log.error("**inside saveFullWorkout in workoutService, workoutRequest is {}",
        // workoutRequestDTO);
        WorkoutDetailsDTO workoutDetailsDTO = workoutRequestDTO.getWorkoutDetails();
        List<WorkoutExerciseDetailsDTO> workoutExerciseDetails = workoutRequestDTO.getWorkoutExerciseDetails();
        User user = userService.getUserByUsername(workoutDetailsDTO.getUsername()).orElse(null);
        // log.error("user in save workout is {}", user);
        Workout workout = this.saveWorkout(user, workoutDetailsDTO.getTitle(), workoutDetailsDTO.getCompletedAt(),
                workoutDetailsDTO.getWorkoutNotes());
        // log.error("workout in service is {}", workout);
        for (int i = 0; i < workoutExerciseDetails.size(); i++) {
            WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO = workoutExerciseDetails.get(i);
            Exercise exercise = exerciseService.getExerciseById(workoutExerciseDetailsDTO.getExerciseId());
            Integer exerciseOrder = i + 1;
            WorkoutExercise workoutExercise = workoutExerciseService.saveWorkoutExercise(workout, exercise,
                    exerciseOrder);

            List<ExerciseSetDTO> exerciseSetDTOs = workoutExerciseDetailsDTO.getSets();

            for (int j = 0; j < exerciseSetDTOs.size(); j++) {
                Integer setOrder = j + 1;
                ExerciseSetDTO setDTO = exerciseSetDTOs.get(j);
                List<SetSegmentDTO> segmentDTOs = setDTO.getSegments();
                // log.error("inside ex serv. workout save. exercise set is {}", setDTO);
                try {
                    ExerciseSet exerciseSet = exerciseSetService.saveExerciseSet(workoutExercise, setOrder);
                    for (int k = 0; k < segmentDTOs.size(); k++) {
                        SetSegmentDTO segmentDTO = segmentDTOs.get(k);
                        Integer segmentOrder = k + 1;
                        Float pace = setPaceForWorkout(segmentDTO.getDistanceMeters(), segmentDTO.getDurationSeconds());

                        try {
                            setSegmentService.saveSetSegment(exerciseSet, segmentOrder,
                                    segmentDTO.getReps(), segmentDTO.getWeight(),
                                    segmentDTO.getDurationSeconds(), segmentDTO.getDistanceMeters(),
                                    segmentDTO.getDistanceMeasurement(),
                                    pace);
                        } catch (Exception e) {
                            log.error("Error saving set segment: {}", segmentDTO, e);
                            throw e;
                        }
                    }

                } catch (Exception e) {
                    log.error("Error saving exercise set: {}", setDTO, e);
                    throw e; // rethrow to preserve behavior
                }
            }
        }
        // wait to do this after everything has been saved so you have the latest for
        // the records
        for (int i = 0; i < workoutExerciseDetails.size(); i++) {
            // System.out.println("🏋️‍♀️ Inside Workout Service, part where I save the
            // record");
            WorkoutExerciseDetailsDTO workoutExerciseDetailsDTO = workoutExerciseDetails.get(i);
            Exercise exercise = exerciseService.getExerciseById(workoutExerciseDetailsDTO.getExerciseId());
            ExerciseRecord exerciseRecord = exerciseRecordService.saveExerciseRecord(workout, exercise,
                    workoutExerciseDetailsDTO, user);
            // System.out.println("🏋️‍♀️ ********* Workout service - exercise record is" +
            // exerciseRecord);
        }
    }

    private Float setPaceForWorkout(Float distanceMeters, Integer durationSeconds) {
        float pace;

        if (distanceMeters == null || durationSeconds == null || durationSeconds == 0) {
            return (float) 0;
        }
        pace = calculatePace(distanceMeters, durationSeconds);
        // Round to 2 decimal places
        BigDecimal roundedPace = new BigDecimal(pace).setScale(2, RoundingMode.HALF_UP);
        return roundedPace.floatValue();
    }

    WorkoutDetailsDTO addValuesToWorkoutDetailsDTO(Workout workout) {
        WorkoutDetailsDTO workoutDetailsDTO = new WorkoutDetailsDTO();
        workoutDetailsDTO.setUsername(workout.getUser().getUsername());
        workoutDetailsDTO.setCompletedAt(workout.getCompletedAt());
        workoutDetailsDTO.setTitle(workout.getTitle());
        workoutDetailsDTO.setWorkoutNotes(workout.getWorkoutNotes());
        return workoutDetailsDTO;
    };

    ExerciseSetDTO addValuesToExerciseSetDTO(ExerciseSet exerciseSet) {
        ExerciseSetDTO exerciseSetDTODetails = new ExerciseSetDTO();
        List<SetSegment> segments = setSegmentService.getAllSetSegmentsByExerciseSet(exerciseSet);
        List<SetSegmentDTO> segmentDTOs = new ArrayList<>();
        for (SetSegment segment : segments) {
            SetSegmentDTO setSegmentDTO = addValuesToSetSegmentDTO(segment);
            segmentDTOs.add(setSegmentDTO);
        }
        exerciseSetDTODetails.setSegments(segmentDTOs);
        return exerciseSetDTODetails;
    }

    SetSegmentDTO addValuesToSetSegmentDTO(SetSegment setSegment) {
        SetSegmentDTO setSegmentDTODetails = new SetSegmentDTO();
        setSegmentDTODetails.setReps(setSegment.getReps());
        setSegmentDTODetails.setWeight(setSegment.getWeight());
        setSegmentDTODetails.setDurationSeconds(setSegment.getDurationSeconds());
        setSegmentDTODetails.setDistanceMeters(setSegment.getDistanceMeters());
        setSegmentDTODetails.setDistanceMeasurement(setSegment.getDistanceMeasurement());
        setSegmentDTODetails.setPacePerMile(setSegment.getPacePerMile());
        return setSegmentDTODetails;
    }

    public Float calculatePace(Float distanceMeters, Integer durationSeconds) {
        Float pace = 0f;
        if (distanceMeters == null || durationSeconds == null) {
            return pace;
        }
        if (distanceMeters > 0 && durationSeconds > 0) {
            pace = ((float) (durationSeconds / 60)) / ((float) (distanceMeters / 1609.34));
        }
        // System.out.println("🏋️‍♀️ WS - pace - pace is and distanceMeters is" +
        // pace);
        // System.out.println("🏋️‍♀️ WS - pace - distanceMeters is" + distanceMeters);
        return pace;
    }
}
