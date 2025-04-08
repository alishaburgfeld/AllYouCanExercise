package com.allyoucanexercise.back_end.workout;

import jakarta.validation.constraints.NotNull;

public class WorkoutIndividualExerciseDetailsDTO {

    private int workoutId;

    @NotNull
    private int exerciseId;

    private int workoutExerciseId;

    private int exerciseSetId;

    @NotNull
    private Integer exerciseOrder;

    @NotNull
    private Integer setOrder;
    private Integer sets;
    private Integer reps;
    private Float weight;
    private Integer durationSeconds;
    private Integer distanceMeters;

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getWorkoutExerciseId() {
        return workoutExerciseId;
    }

    public void setWorkoutExerciseId(int workoutExerciseId) {
        this.workoutExerciseId = workoutExerciseId;
    }

    public int getExerciseSetId() {
        return exerciseSetId;
    }

    public void setExerciseSetId(int exerciseSetId) {
        this.exerciseSetId = exerciseSetId;
    }

    public Integer getSetOrder() {
        return setOrder;
    }

    public void setSetOrder(Integer setOrder) {
        this.setOrder = setOrder;
    }

    public Integer getExerciseOrder() {
        return exerciseOrder;
    }

    public void setExerciseOrder(Integer exerciseOrder) {
        this.exerciseOrder = exerciseOrder;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Integer getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(Integer distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

}
