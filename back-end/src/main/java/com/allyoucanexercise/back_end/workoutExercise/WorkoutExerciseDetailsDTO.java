package com.allyoucanexercise.back_end.workoutExercise;

import java.util.List;

import com.allyoucanexercise.back_end.exerciseSet.ExerciseSetDTO;

public class WorkoutExerciseDetailsDTO {
    private Long id;
    private List<ExerciseSetDTO> sets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ExerciseSetDTO> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSetDTO> sets) {
        this.sets = sets;
    }
}
