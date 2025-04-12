package com.allyoucanexercise.back_end.exercise;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseGroup exerciseGroup;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseType exerciseType;

    @NotNull
    @Column(nullable = false)
    private String description;

    protected Exercise() {
    }

    public Exercise(String name, ExerciseGroup exerciseGroup, ExerciseType exerciseType, String description) {
        this.name = name;
        this.exerciseGroup = exerciseGroup;
        this.exerciseType = exerciseType;
        this.description = description;
    }

    // could also create a constructor that includes id in case I need that for unit
    // tests.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExerciseGroup getExerciseGroup() {
        return exerciseGroup;
    }

    public void setExerciseGroup(ExerciseGroup exerciseGroup) {
        this.exerciseGroup = exerciseGroup;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exerciseGroup=" + exerciseGroup +
                ", exerciseType=" + exerciseType +
                ", description='" + description + '\'' +
                '}';
    }
}
