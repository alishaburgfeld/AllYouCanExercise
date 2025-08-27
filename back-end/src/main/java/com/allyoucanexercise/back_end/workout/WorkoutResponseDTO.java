package com.allyoucanexercise.back_end.workout;

import java.util.List;

import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;

public class WorkoutResponseDTO {

    private WorkoutDetailsDTO workoutDetails;

    private List<WorkoutExerciseDetailsDTO> workoutExerciseDetails;

    public WorkoutResponseDTO() {
    }

    public WorkoutDetailsDTO getWorkoutDetails() {
        return workoutDetails;
    }

    public void setWorkoutDetails(WorkoutDetailsDTO workoutDetails) {
        this.workoutDetails = workoutDetails;
    }

    public List<WorkoutExerciseDetailsDTO> getWorkoutExerciseDetails() {
        return workoutExerciseDetails;
    }

    public void setWorkoutExerciseDetails(List<WorkoutExerciseDetailsDTO> workoutExerciseDetails) {
        this.workoutExerciseDetails = workoutExerciseDetails;
    }

    @Override
    public String toString() {
        return "WorkoutResponseDTO{" +
                "workoutDetails=" + (workoutDetails != null ? workoutDetails.toString() : "null") +
                ", workoutExerciseDetails="
                + (workoutExerciseDetails != null ? workoutExerciseDetails.toString() : "null") +
                '}';
    }

}
