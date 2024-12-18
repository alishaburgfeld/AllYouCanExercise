package com.allyoucanexercise.back_end.exercise;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class ExerciseRepository {

    private final JdbcClient jdbcClient;

    public ExerciseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Exercise> findAll() {
        return jdbcClient.sql("select * from exercise")
                .query(Exercise.class)
                .list();
    }

    public Optional<Exercise> findById(Integer id) {
        return jdbcClient.sql("SELECT id,name,exercise_type,description FROM Exercise WHERE id = :id" )
                .param("id", id)
                .query(Exercise.class)
                .optional();
    }

    public void create(Exercise exercise) {
        var updated = jdbcClient.sql("INSERT INTO Exercise(id,name,exercise_type,description) values(?,?,?,?)")
                .params(List.of(exercise.id(),exercise.name(),exercise.exerciseType(),exercise.description().toString()))
                .update();
        
        // the sql .update function returns the amount of rows affected.

        Assert.state(updated == 1, "Failed to create exercise " + exercise.name());
    }

    public void update(Exercise exercise, Integer id) {
        var updated = jdbcClient.sql("update exercise set name = ?, exercise_type = ?, description = ? where id = ?")
                .params(List.of(exercise.name(),exercise.exerciseType(),exercise.description().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update exercise " + exercise.name());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from exercise where id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete exercise " + id);
    }

    public int count() {
        return jdbcClient.sql("select * from exercise").query().listOfRows().size();
    }

    public void saveAll(List<Exercise> exercises) {
        exercises.stream().forEach(this::create);
    }

    public List<Exercise> findByExerciseType(String exerciseType) {
        return jdbcClient.sql("select * from exercise where exercise_type = :exerciseType")
                .param("exercise_type", exerciseType)
                .query(Exercise.class)
                .list();
    }

}
