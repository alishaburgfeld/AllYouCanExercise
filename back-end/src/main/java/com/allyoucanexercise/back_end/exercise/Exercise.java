package com.allyoucanexercise.back_end.exercise;

import jakarta.validation.constraints.NotEmpty;

public record Exercise(
    Integer id, 
    @NotEmpty
    String name, 
    ExerciseType ExerciseType, 
    String description) {
    
    // eventually add equipment, but not sure what type it would be since something like pushups would be none, home gym, and full gym
}
