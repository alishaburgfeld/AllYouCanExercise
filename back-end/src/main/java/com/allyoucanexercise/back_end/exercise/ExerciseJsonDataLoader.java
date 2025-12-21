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
    // CommandLineRunner: Marks it to be executed after the application context is
    // fully initialized

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
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/exercises.json")) {
            Exercises allExercises = objectMapper.readValue(inputStream, Exercises.class);
            if (exerciseRepository.count() < allExercises.exercises().size()) {
                // need to look up inputStream and TypeReference
                log.info("Reading {} exercises from JSON data and saving to MySQL Database.",
                        allExercises.exercises().size());
                // how does this know how many exercises to put in the {}
                // thats the format of the log function. you can put a variable after the comma
                // and it will fill in the {}. Very similar to string format.

                // this will just check if the db has less items than my exercise.json, and if
                // so then it will add new entries and update existing, but it will not delete
                // any of the old ones in my db that are no longer on my exerises.json

                exerciseRepository.saveAll(allExercises.exercises());

                // use this if I want to completely wipe the DB and then start fresh from
                // exercises.json:
                // this won't work b/c of exercise record, etc dependencies.
                // exerciseRepository.saveAll(allExercises.exercises());
                // exerciseRepository.deleteAll();
                // exerciseRepository.saveAll(allExercises.exercises());

            } else {
                log.info("Not loading Exercises from JSON data because the collection contains data.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}