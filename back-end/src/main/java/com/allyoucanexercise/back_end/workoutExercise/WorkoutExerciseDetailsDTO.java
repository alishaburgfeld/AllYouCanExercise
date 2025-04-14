package com.allyoucanexercise.back_end.workoutExercise;

import java.util.List;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;

public class WorkoutExerciseDetailsDTO {
    private Long exerciseId;
    private List<ExerciseSetDTO> sets;

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public List<ExerciseSetDTO> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSetDTO> sets) {
        this.sets = sets;
    }
}
