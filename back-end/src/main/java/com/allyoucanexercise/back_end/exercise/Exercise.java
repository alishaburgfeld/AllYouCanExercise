package com.allyoucanexercise.back_end.exercise;

public record Exercise(Integer id, String name, Group group, String description) {
    
    // eventually add equipment, but not sure what type it would be since something like pushups would be none, home gym, and full gym
}
