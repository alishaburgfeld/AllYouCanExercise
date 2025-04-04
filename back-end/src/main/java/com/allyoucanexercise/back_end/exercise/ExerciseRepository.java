package com.allyoucanexercise.back_end.exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class ExerciseRepository {

    private final JdbcClient jdbcClient;

    private static final Logger log = LoggerFactory.getLogger(ExerciseRepository.class);

    public ExerciseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Exercise> findAll() {
        return jdbcClient.sql("select * from exercise")
                .query(Exercise.class)
                .list();
    }

    public Optional<Exercise> findById(Integer id) {
        return jdbcClient
                .sql("SELECT id,name,exercise_group,exercise_type,description,picture FROM exercise WHERE id = :id")
                .param("id", id)
                .query(Exercise.class)
                .optional();
    }

    public void create(Exercise exercise) {
        var updated = jdbcClient
                .sql("INSERT INTO exercise(name,exercise_group,exercise_type,description,picture) values(?,?,?,?,?)")
                .params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                        exercise.exerciseType().toString(), exercise.description(),
                        exercise.picture() != null ? exercise.picture() : ""))
                .update();

        // list does not allow null values, so had to add in the default "" value for
        // picture
        // the sql .update function returns the amount of rows affected.

        Assert.state(updated == 1, "Failed to create exercise " + exercise.name());
    }

    public void update(Exercise exercise, Integer id) {
        var updated = jdbcClient.sql(
                "update exercise set name = ?, exercise_group = ?, exercise_type = ?, description = ?, picture = ? where id = ?")
                .params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                        exercise.exerciseType().toString(), exercise.description(),
                        exercise.picture() != null ? exercise.picture() : "", id))
                .update();

        Assert.state(updated == 1, "Failed to update exercise");
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from exercise where id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete exercise");
    }

    public int count() {
        return jdbcClient.sql("select * from exercise").query().listOfRows().size();
    }

    public void saveAll(List<Exercise> exercises) {
        exercises.stream().forEach(this::create);
    }

    public List<Exercise> findByExerciseType(String exercise_type) {
        return jdbcClient.sql("select * from exercise where exercise_type = :exercise_type")
                .param("exercise_type", exercise_type)
                .query(Exercise.class)
                .list();
    }

    public List<Exercise> findByExerciseGroup(String exercise_group) {
        return jdbcClient.sql("select * from exercise where exercise_group = :exercise_group")
                .param("exercise_group", exercise_group)
                .query(Exercise.class)
                .list();
    }

}
