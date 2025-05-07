package com.allyoucanexercise.back_end.workout;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.allyoucanexercise.back_end.workoutExercise.WorkoutExerciseDetailsDTO;
import com.allyoucanexercise.back_end.workout.WorkoutDetailsDTO;
// To parse that JSON into Java objects automatically in your Spring Boot 
// controller (@RequestBody), you need a class that matches the structure 
// of that top-level JSON object.

// Spring Boot uses Jackson (the JSON parser) to automatically convert your 
// JSON request into Java objects. Jackson will:
// Look at the JSON keys like "workoutDetails" and "workoutExerciseDetails"
// Match them to fields in your DTO
// Use nested classes to keep your data structured

public class WorkoutRequestDTO {
    @JsonProperty("workoutDetails")
    private WorkoutDetailsDTO workoutDetails;

    @JsonProperty("workoutExerciseDetails")
    private List<WorkoutExerciseDetailsDTO> workoutExerciseDetails;

    public WorkoutRequestDTO() {
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
        return "WorkoutRequestDTO{" +
                "workoutDetails=" + (workoutDetails != null ? workoutDetails.toString() : "null") +
                ", workoutExerciseDetails="
                + (workoutExerciseDetails != null ? workoutExerciseDetails.toString() : "null") +
                '}';
    }

}
