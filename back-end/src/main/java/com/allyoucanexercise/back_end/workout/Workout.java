package com.allyoucanexercise.back_end.workout;

import java.sql.Timestamp;
import jakarta.validation.constraints.NotEmpty;

public class Workout {
    private int id;
    @NotEmpty
    private int userId;
    private String title;
    private Timestamp createdAt;
    private Timestamp completedAt;

    // timestamp:
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/Timestamp.html

    public Workout() {
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }
}
