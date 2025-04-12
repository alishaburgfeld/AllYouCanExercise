package com.allyoucanexercise.back_end.workout;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.allyoucanexercise.back_end.workout.Workout;
import com.allyoucanexercise.back_end.workout.WorkoutRepository;

@Repository
public class WorkoutRepository {
    private final JdbcClient jdbcClient;

    private static final Logger log = LoggerFactory.getLogger(WorkoutRepository.class);

    public WorkoutRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Workout> findAll() {
        return jdbcClient.sql("select * from workout")
                .query(Workout.class)
                .list();
    }

    public Optional<Workout> findById(Integer id) {
        return jdbcClient
                .sql("SELECT id, user_Id, title, created_at, completed_at FROM workout WHERE id = :id")
                .param("id", id)
                .query(Workout.class)
                .optional();
    }

    public void create(Workout workout) {
        if (workout.getUserId() == null) {
            throw new IllegalArgumentException("userId must not be null");
        }

        var updated = jdbcClient
                .sql("INSERT INTO workout(user_id, title, completed_at) values(?,?,?)")
                .params(List.of(workout.getUserId(), workout.getTitle() != null ? workout.getTitle() : "",
                        workout.getCompletedAt()))
                .update();

        Assert.state(updated == 1, "Failed to create workout");
    }

    public void update(Workout workout, Integer id) {
        var updated = jdbcClient.sql(
                "update workout set user_id = ?, title = ?, completed_at = ? where id = ?")
                .params(List.of(workout.getUserId(), workout.getTitle() != null ? workout.getTitle() : "",
                        workout.getCompletedAt(), id))
                .update();

        Assert.state(updated == 1, "Failed to update workout");
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from workout where id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete workout");
    }

    public List<Workout> findByUserId(Integer user_id) {
        log.error("in find by User");
        log.error("user_id is {}", user_id);
        return jdbcClient.sql("select * from workout where user_id = :user_id")
                .param("user_id", user_id)
                .query(Workout.class)
                .list();
    }
}
