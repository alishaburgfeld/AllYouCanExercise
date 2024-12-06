// package com.allyoucanexercise.back_end.exercise;

// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.io.IOException;
// import java.io.InputStream;

// @Component
// public class ExerciseJsonDataLoader implements CommandLineRunner {

//     private static final Logger log = LoggerFactory.getLogger(ExerciseJsonDataLoader.class);
//     private final ObjectMapper objectMapper;
//     private final ExerciseRepository exerciseRepository;

//     public ExerciseJsonDataLoader(ObjectMapper objectMapper, @Qualifier("jdbcExerciseRepository") ExerciseRepository exerciseRepository) {
//         this.objectMapper = objectMapper;
//         this.exerciseRepository = exerciseRepository;
//     }

//     @Override
//     public void exercise(String... args) throws Exception {
//         if(exerciseRepository.count() == 0) {
//             try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/exercises.json")) {
//                 Exercises allExercises = objectMapper.readValue(inputStream, Exercises.class);
//                 log.info("Reading {} exercises from JSON data and saving to in-memory collection.", allExercises.exercises().size());
//                 exerciseRepository.saveAll(allExercises.exercises());
//             } catch (IOException e) {
//                 throw new RuntimeException("Failed to read JSON data", e);
//             }
//         } else {
//             log.info("Not loading Exercises from JSON data because the collection contains data.");
//         }
//     }

// }