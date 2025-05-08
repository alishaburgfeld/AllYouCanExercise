package com.allyoucanexercise.back_end.exerciseSet;

public class ExerciseSetDTO {
    private Integer reps;
    private Float weight;
    private Integer durationSeconds; // durationSeconds in seconds
    private Float distanceMeters; // distanceMeters in meters

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Float getWeight() {
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

    public Float getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(Float distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    @Override
    public String toString() {
        return "ExerciseSetDTO{" +
                "reps=" + reps +
                ", weight=" + weight +
                ", durationSeconds=" + durationSeconds +
                ", distanceMeters=" + distanceMeters +
                '}';
    }

}
