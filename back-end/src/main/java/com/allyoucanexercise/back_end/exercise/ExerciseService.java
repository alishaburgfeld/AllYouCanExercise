package com.allyoucanexercise.back_end.exercise;

import java.util.List;
// import java.util.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));
    }

    public List<Exercise> getExercisesByType(ExerciseType exerciseType) {
        return exerciseRepository.findAllByExerciseType(exerciseType);
    }

    public List<Exercise> getExercisesByGroup(ExerciseGroup exerciseGroup) {
        return exerciseRepository.findAllByExerciseGroup(exerciseGroup);
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Exercise updateExercise(Exercise exercise, Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }

        exercise.setId(id); // Make sure the entity has the correct ID. This ensures that the JPA context
                            // understands this is an update, not a new insert.
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }
        exerciseRepository.deleteById(id);
    }
}
