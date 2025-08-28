package com.allyoucanexercise.back_end.workoutExercise;

import java.util.List;

import com.allyoucanexercise.back_end.exercise.ExerciseGroup;
import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;

public class WorkoutExerciseDetailsDTO {
    private Long exerciseId;
    private String exerciseName;
    private ExerciseGroup exerciseGroup;
    private List<ExerciseSetDTO> sets;

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public ExerciseGroup getExerciseGroup() {
        return exerciseGroup;
    }

    public void setExerciseGroup(ExerciseGroup exerciseGroup) {
        this.exerciseGroup = exerciseGroup;
    }

    public List<ExerciseSetDTO> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSetDTO> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "WorkoutExerciseDetailsDTO{" +
                "exerciseId=" + exerciseId +
                "exerciseName=" + exerciseName +
                "exerciseGroup=" + exerciseGroup +
                ", sets=" + (sets != null ? sets.toString() : "null") +
                '}';
    }

}
