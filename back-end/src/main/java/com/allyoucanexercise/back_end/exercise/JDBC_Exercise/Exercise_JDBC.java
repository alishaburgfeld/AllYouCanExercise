package com.allyoucanexercise.back_end.exercise;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record Exercise(
    Integer id, 
    @NotEmpty
    String name, 
    @NotNull
    ExerciseGroup exerciseGroup,
    ExerciseType exerciseType, 
    String description,
    String picture) {
    
   }
