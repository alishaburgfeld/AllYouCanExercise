package com.allyoucanexercise.back_end.exercise;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ExerciseJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ExerciseJsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final ExerciseRepository exerciseRepository;

    public ExerciseJsonDataLoader(ObjectMapper objectMapper, ExerciseRepository exerciseRepository) {
        this.objectMapper = objectMapper;
        // object mapper deserializes json into objects
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(exerciseRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/exercises.json")) {
                // need to look up inputStream and TypeReference
                Exercises allExercises = objectMapper.readValue(inputStream, Exercises.class);
                log.info("Reading {} exercises from JSON data and saving to MySQL Database.", allExercises.exercises().size());
                // how does this know how many exercises to put in the {}
                // thats the format of the log function. you can put a variable after the comma and it will fill in the  {}. Very similar to string format.
                exerciseRepository.saveAll(allExercises.exercises());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Exercises from JSON data because the collection contains data.");
        }
    }

}