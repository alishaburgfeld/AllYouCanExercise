package com.allyoucanexercise.back_end.workoutExercise;

import jakarta.validation.constraints.NotNull;

public class WorkoutExercise {
    private int id;
    @NotNull
    private Integer workoutId;
    @NotNull
    private Integer exerciseId;
    private Integer exerciseOrder;

    public int getId() {
        return id;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getExerciseOrder() {
        return exerciseOrder;
    }

    public void setExerciseOrder(Integer exerciseOrder) {
        this.exerciseOrder = exerciseOrder;
    }

}
