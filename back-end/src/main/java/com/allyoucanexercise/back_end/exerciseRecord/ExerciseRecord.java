package com.allyoucanexercise.back_end.exerciseRecord;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import com.allyoucanexercise.back_end.exercise.Exercise;
import com.allyoucanexercise.back_end.user.User;
import com.allyoucanexercise.back_end.workout.Workout;

@Entity
@Table(name = "exercise_record")
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "last_exercised", nullable = false)
    private LocalDateTime lastExercised;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "last_exercised_workout_id", nullable = false)
    private Workout lastExercisedWorkout;

    @Column(name = "max_sets")
    private Integer maxSets;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "max_sets_workout_id", nullable = false)
    private Workout maxSetsWorkout;

    @Column(name = "max_reps")
    private Integer maxReps;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "max_reps_workout_id", nullable = false)
    private Workout maxRepsWorkout;

    @Column(name = "max_weight")
    private Float maxWeight;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "max_weight_workout_id", nullable = false)
    private Workout maxWeightWorkout;

    @Column(name = "max_volume")
    private Float maxVolume;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "max_volume_workout_id", nullable = false)
    private Workout maxVolumeWorkout;

    @Column(name = "max_duration_seconds")
    private Integer maxDurationSeconds;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "max_duration_workout_id", nullable = false)
    private Workout maxDurationWorkout;

    @Column(name = "max_distance_meters")
    private Float maxDistanceMeters;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "max_distance_workout_id", nullable = false)
    private Workout maxDistanceWorkout;

    @Column(name = "max_pace_per_mile")
    private Float maxPacePerMile;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "max_pace_workout_id", nullable = false)
    private Workout maxPaceWorkout;

    public ExerciseRecord(Exercise exercise, User user, LocalDateTime lastExercised, Workout lastExercisedWorkout,
            Integer maxSets, Workout maxSetsWorkout, Integer maxReps,
            Workout maxRepsWorkout, Float maxWeight, Workout maxWeightWorkout,
            Float maxVolume, Workout maxVolumeWorkout,
            Integer maxDurationSeconds, Workout maxDurationWorkout, Float maxDistanceMeters, Workout maxDistanceWorkout,
            Float maxPacePerMile, Workout maxPaceWorkout) {
        this.exercise = exercise;
        this.user = user;
        this.lastExercised = lastExercised;
        this.lastExercisedWorkout = lastExercisedWorkout;
        this.maxSets = maxSets;
        this.maxSetsWorkout = maxSetsWorkout;
        this.maxReps = maxReps;
        this.maxRepsWorkout = maxRepsWorkout;
        this.maxWeight = maxWeight;
        this.maxWeightWorkout = maxWeightWorkout;
        this.maxVolume = maxVolume;
        this.maxVolumeWorkout = maxVolumeWorkout;
        this.maxDurationSeconds = maxDurationSeconds;
        this.maxDurationWorkout = maxDurationWorkout;
        this.maxDistanceMeters = maxDistanceMeters;
        this.maxDistanceWorkout = maxDistanceWorkout;
        this.maxPacePerMile = maxPacePerMile;
        this.maxPaceWorkout = maxPaceWorkout;
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

    public LocalDateTime getLastExercised() {
        return lastExercised;
    }

    public void setLastExercised(LocalDateTime lastExercised) {
        this.lastExercised = lastExercised;
    }

    public void setLastExercisedWorkout(Workout lastExercisedWorkout) {
        this.lastExercisedWorkout = lastExercisedWorkout;
    }

    public Workout getLastExercisedWorkout() {
        return lastExercisedWorkout;
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

    public void setMaxPacePerMile(Float maxPacePerMile) {
        this.maxPacePerMile = maxPacePerMile;
    }

    public Float getMaxPacePerMile() {
        return maxPacePerMile;
    }

    public void setMaxDistanceMeters(Float maxDistanceMeters) {
        this.maxDistanceMeters = maxDistanceMeters;
    }

    public Workout getMaxSetsWorkout() {
        return maxSetsWorkout;
    }

    public Workout getMaxRepsWorkout() {
        return maxRepsWorkout;
    }

    public Workout getMaxWeightWorkout() {
        return maxWeightWorkout;
    }

    public Workout getMaxVolumeWorkout() {
        return maxVolumeWorkout;
    }

    public Workout getMaxDistanceWorkout() {
        return maxDistanceWorkout;
    }

    public Workout getMaxDurationWorkout() {
        return maxDurationWorkout;
    }

    public Workout getMaxPaceWorkout() {
        return maxPaceWorkout;
    }

    // Setters
    public void setMaxSetsWorkout(Workout maxSetsWorkout) {
        this.maxSetsWorkout = maxSetsWorkout;
    }

    public void setMaxRepsWorkout(Workout maxRepsWorkout) {
        this.maxRepsWorkout = maxRepsWorkout;
    }

    public void setMaxWeightWorkout(Workout maxWeightWorkout) {
        this.maxWeightWorkout = maxWeightWorkout;
    }

    public void setMaxVolumeWorkout(Workout maxVolumeWorkout) {
        this.maxVolumeWorkout = maxVolumeWorkout;
    }

    public void setMaxDistanceWorkout(Workout maxDistanceWorkout) {
        this.maxDistanceWorkout = maxDistanceWorkout;
    }

    public void setMaxDurationWorkout(Workout maxDurationWorkout) {
        this.maxDurationWorkout = maxDurationWorkout;
    }

    public void setMaxPaceWorkout(Workout maxPaceWorkout) {
        this.maxPaceWorkout = maxPaceWorkout;
    }
}
