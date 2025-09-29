package com.allyoucanexercise.back_end.setSegment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSet;

@Entity
@Table(name = "set_segment")
public class SetSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "exercise_set_id", nullable = false)
    private ExerciseSet exerciseSet;

    @Column(nullable = false)
    private Integer segmentOrder;

    private Integer reps;
    private Float weight;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "distance_meters")
    private Float distanceMeters;

    @Enumerated(EnumType.STRING)
    @Column(name = "distance_measurement")
    private DistanceMeasurement distanceMeasurement;

    @Column(name = "pace_per_mile")
    private Float pacePerMile;

    public SetSegment(ExerciseSet exerciseSet, Integer segmentOrder, Integer reps, Float weight,
            Integer durationSeconds, Float distanceMeters, DistanceMeasurement distanceMeasurement, Float pacePerMile) {
        this.exerciseSet = exerciseSet;
        this.segmentOrder = segmentOrder;
        this.reps = reps;
        this.weight = weight;
        this.durationSeconds = durationSeconds;
        this.distanceMeters = distanceMeters;
        this.distanceMeasurement = distanceMeasurement;
        this.pacePerMile = pacePerMile;
    }

    protected SetSegment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseSet getExerciseSet() {
        return exerciseSet;
    }

    public void setExerciseSet(ExerciseSet exerciseSet) {
        this.exerciseSet = exerciseSet;
    }

    public Integer getSegmentOrder() {
        return segmentOrder;
    }

    public void setSegmentOrder(Integer segmentOrder) {
        this.segmentOrder = segmentOrder;
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

    public DistanceMeasurement getDistanceMeasurement() {
        return distanceMeasurement;
    }

    public void setDistanceMeasurement(DistanceMeasurement distanceMeasurement) {
        this.distanceMeasurement = distanceMeasurement;
    }

    public Float getPacePerMile() {
        return pacePerMile;
    }

    public void setPacePerMile(Float pacePerMile) {
        this.pacePerMile = pacePerMile;
    }
}
