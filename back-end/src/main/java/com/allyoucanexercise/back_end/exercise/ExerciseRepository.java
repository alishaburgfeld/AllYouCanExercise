package com.allyoucanexercise.back_end.exercise;

import java.util.List;
import java.util.Optional;

// In case I run into problems with my pom.xml file: https://stackoverflow.com/questions/63841351/the-import-org-springframework-data-jpa-repository-jparepository-cant-be-resolv
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    public Optional<Exercise> findById(Long id);

    public List<Exercise> findAllByExerciseType(ExerciseType exerciseType);

    public List<Exercise> findAllByExerciseGroup(ExerciseGroup exerciseGroup);

    // All of htese are baked in and don't need to be included unless overriding:
    // public Exercise save(Exercise); --used for update too
    // public void saveAll(List<Exercise> exercises);
    // public void deleteById(Long id);
    // public long count();

}
