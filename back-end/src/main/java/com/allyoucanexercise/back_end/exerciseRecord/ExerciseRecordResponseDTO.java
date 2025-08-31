package com.allyoucanexercise.back_end.exerciseRecord;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class ExerciseRecordResponseDTO {

    private Long exerciseId;
    private LocalDateTime lastExercised;
    private Long lastExercisedWorkoutId;
    private Integer maxSets;
    private Long maxSetsWorkoutId;
    private Integer maxReps;
    private Long maxRepsWorkoutId;
    private Float maxWeight;
    private Long maxWeightWorkoutId;
    private Float maxVolume;
    private Long maxVolumeWorkoutId;
    private Integer maxDurationSeconds;
    private Long maxDurationWorkoutId;
    private Float maxDistanceMeters;
    private Long maxDistanceWorkoutId;
    private Float maxPacePerMile;
    private Long maxPaceWorkoutId;

    public ExerciseRecordResponseDTO() {

    };

    public ExerciseRecordResponseDTO(
            Long exerciseId,
            LocalDateTime lastExercised,
            Long lastExercisedWorkoutId,
            Integer maxSets,
            Long maxSetsWorkoutId,
            Integer maxReps,
            Long maxRepsWorkoutId,
            Float maxWeight,
            Long maxWeightWorkoutId,
            Float maxVolume,
            Long maxVolumeWorkoutId,
            Integer maxDurationSeconds,
            Long maxDurationWorkoutId,
            Float maxDistanceMeters,
            Long maxDistanceWorkoutId,
            Float maxPacePerMile,
            Long maxPaceWorkoutId) {
        this.exerciseId = exerciseId;
        this.lastExercised = lastExercised;
        this.lastExercisedWorkoutId = lastExercisedWorkoutId;
        this.maxSets = maxSets;
        this.maxSetsWorkoutId = maxSetsWorkoutId;
        this.maxReps = maxReps;
        this.maxRepsWorkoutId = maxRepsWorkoutId;
        this.maxWeight = maxWeight;
        this.maxWeightWorkoutId = maxWeightWorkoutId;
        this.maxVolume = maxVolume;
        this.maxVolumeWorkoutId = maxVolumeWorkoutId;
        this.maxDurationSeconds = maxDurationSeconds;
        this.maxDurationWorkoutId = maxDurationWorkoutId;
        this.maxDistanceMeters = maxDistanceMeters;
        this.maxDistanceWorkoutId = maxDistanceWorkoutId;
        this.maxPacePerMile = maxPacePerMile;
        this.maxPaceWorkoutId = maxPaceWorkoutId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public LocalDateTime getLastExercised() {
        return lastExercised;
    }

    public void setLastExercised(LocalDateTime lastExercised) {
        this.lastExercised = lastExercised;
    }

    public Long getLastExercisedWorkoutId() {
        return lastExercisedWorkoutId;
    }

    public void setLastExercisedWorkoutId(Long lastExercisedWorkoutId) {
        this.lastExercisedWorkoutId = lastExercisedWorkoutId;
    }

    public Integer getMaxSets() {
        return maxSets;
    }

    public void setMaxSets(Integer maxSets) {
        this.maxSets = maxSets;
    }

    public Long getMaxSetsWorkoutId() {
        return maxSetsWorkoutId;
    }

    public void setMaxSetsWorkoutId(Long maxSetsWorkoutId) {
        this.maxSetsWorkoutId = maxSetsWorkoutId;
    }

    public Integer getMaxReps() {
        return maxReps;
    }

    public void setMaxReps(Integer maxReps) {
        this.maxReps = maxReps;
    }

    public Long getMaxRepsWorkoutId() {
        return maxRepsWorkoutId;
    }

    public void setMaxRepsWorkoutId(Long maxRepsWorkoutId) {
        this.maxRepsWorkoutId = maxRepsWorkoutId;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Long getMaxWeightWorkoutId() {
        return maxWeightWorkoutId;
    }

    public void setMaxWeightWorkoutId(Long maxWeightWorkoutId) {
        this.maxWeightWorkoutId = maxWeightWorkoutId;
    }

    public Float getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Float maxVolume) {
        this.maxVolume = maxVolume;
    }

    public Long getMaxVolumeWorkoutId() {
        return maxVolumeWorkoutId;
    }

    public void setMaxVolumeWorkoutId(Long maxVolumeWorkoutId) {
        this.maxVolumeWorkoutId = maxVolumeWorkoutId;
    }

    public Integer getMaxDurationSeconds() {
        return maxDurationSeconds;
    }

    public void setMaxDurationSeconds(Integer maxDurationSeconds) {
        this.maxDurationSeconds = maxDurationSeconds;
    }

    public Long getMaxDurationWorkoutId() {
        return maxDurationWorkoutId;
    }

    public void setMaxDurationWorkoutId(Long maxDurationWorkoutId) {
        this.maxDurationWorkoutId = maxDurationWorkoutId;
    }

    public Float getMaxDistanceMeters() {
        return maxDistanceMeters;
    }

    public void setMaxDistanceMeters(Float maxDistanceMeters) {
        this.maxDistanceMeters = maxDistanceMeters;
    }

    public Long getMaxDistanceWorkoutId() {
        return maxDistanceWorkoutId;
    }

    public void setMaxDistanceWorkoutId(Long maxDistanceWorkoutId) {
        this.maxDistanceWorkoutId = maxDistanceWorkoutId;
    }

    public Float getMaxPacePerMile() {
        return maxPacePerMile;
    }

    public void setMaxPacePerMile(Float maxPacePerMile) {
        this.maxPacePerMile = maxPacePerMile;
    }

    public Long getMaxPaceWorkoutId() {
        return maxPaceWorkoutId;
    }

    public void setMaxPaceWorkoutId(Long maxPaceWorkoutId) {
        this.maxPaceWorkoutId = maxPaceWorkoutId;
    }
}
