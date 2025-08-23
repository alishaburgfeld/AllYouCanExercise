package com.allyoucanexercise.back_end.exerciseRecord;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.user.User;

@Entity
@Table(name = "exercise_record")
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "last_completed", nullable = false)
    private LocalDateTime lastCompleted;

    @Column(name = "max_sets")
    private Integer maxSets;

    @Column(name = "max_reps")
    private Integer maxReps;

    @Column(name = "max_weight")
    private Float maxWeight;

    @Column(name = "max_volume")
    private Float maxVolume;

    @Column(name = "max_duration_seconds")
    private Integer maxDurationSeconds;

    @Column(name = "max_distance_meters")
    private Float maxDistanceMeters;

    public ExerciseRecord(Exercise exercise, User user, Integer maxSets, Integer maxReps, Float maxWeight,
            Float maxVolume,
            Integer maxDurationSeconds, Float maxDistanceMeters) {
        this.exercise = exercise;
        this.user = user;
        this.maxSets = maxSets;
        this.maxReps = maxReps;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.maxDurationSeconds = maxDurationSeconds;
        this.maxDistanceMeters = maxDistanceMeters;
    }

    protected ExerciseRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getLastCompleted() {
        return lastCompleted;
    }

    public void setCompletedAt(LocalDateTime lastCompleted) {
        this.lastCompleted = lastCompleted;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getMaxSets() {
        return maxSets;
    }

    public void setMaxSets(Integer maxSets) {
        this.maxSets = maxSets;
    }

    public Integer getMaxReps() {
        return maxReps;
    }

    public void setMaxReps(Integer maxReps) {
        this.maxReps = maxReps;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Float getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Float maxVolume) {
        this.maxVolume = maxVolume;
    }

    public Integer getMaxDurationSeconds() {
        return maxDurationSeconds;
    }

    public void setMaxDurationSeconds(Integer maxDurationSeconds) {
        this.maxDurationSeconds = maxDurationSeconds;
    }

    public Float getMaxDistanceMeters() {
        return maxDistanceMeters;
    }

    public void setMaxDistanceMeters(Float maxDistanceMeters) {
        this.maxDistanceMeters = maxDistanceMeters;
    }
}
