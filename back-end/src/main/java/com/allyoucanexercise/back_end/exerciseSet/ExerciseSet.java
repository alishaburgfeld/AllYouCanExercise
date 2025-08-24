package com.allyoucanexercise.back_end.exerciseSet;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.allyoucanexercise.back_end.workoutExercise.WorkoutExercise;

@Entity
@Table(name = "exercise_set")
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExercise workoutExercise;

    @Column(nullable = false)
    private Integer setOrder;

    private Integer reps;
    private Float weight;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "distance_meters")
    private Float distanceMeters;

    @Column(name = "distance_measurement")
    private String distanceMeasurement;

    @Column(name = "pace_per_mile")
    private Float pacePerMile;

    public ExerciseSet(WorkoutExercise workoutExercise, Integer setOrder, Integer reps, Float weight,
            Integer durationSeconds, Float distanceMeters, String distanceMeasurement, Float pacePerMile) {
        this.workoutExercise = workoutExercise;
        this.setOrder = setOrder;
        this.reps = reps;
        this.weight = weight;
        this.durationSeconds = durationSeconds;
        this.distanceMeters = distanceMeters;
        this.distanceMeasurement = distanceMeasurement;
        this.pacePerMile = pacePerMile;
    }

    protected ExerciseSet() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutExercise getWorkoutExercise() {
        return workoutExercise;
    }

    public void setWorkoutExercise(WorkoutExercise workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    public Integer getSetOrder() {
        return setOrder;
    }

    public void setSetOrder(Integer setOrder) {
        this.setOrder = setOrder;
    }

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

    public String getDistanceMeasurement() {
        return distanceMeasurement;
    }

    public void setDistanceMeasurement(String distanceMeasurement) {
        this.distanceMeasurement = distanceMeasurement;
    }

    public Float getPacePerMile() {
        return pacePerMile;
    }

    public void setPacePerMile(Float pacePerMile) {
        this.pacePerMile = pacePerMile;
    }
}