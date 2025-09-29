package com.allyoucanexercise.back_end.workoutExercise;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import com.allyoucanexercise.back_end.workout.Workout;
import com.allyoucanexercise.back_end.exercise.Exercise;

@Entity
@Table(name = "workout_exercise")
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @NotNull
    @Column(name = "exercise_order", nullable = false)
    private Integer exerciseOrder;

    protected WorkoutExercise() {

    }

    public WorkoutExercise(Workout workout, Exercise exercise, Integer exerciseOrder) {
        this.workout = workout;
        this.exercise = exercise;
        this.exerciseOrder = exerciseOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workout getWorkout() {
        return this.workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getExerciseOrder() {
        return exerciseOrder;
    }

    public void setExerciseOrder(Integer exerciseOrder) {
        this.exerciseOrder = exerciseOrder;
    }

}