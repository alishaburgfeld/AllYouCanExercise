package com.allyoucanexercise.back_end.exerciseSet;

import jakarta.persistence.*;
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

    public ExerciseSet(WorkoutExercise workoutExercise, Integer setOrder) {
        this.workoutExercise = workoutExercise;
        this.setOrder = setOrder;
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

}