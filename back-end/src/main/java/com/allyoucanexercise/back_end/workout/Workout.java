package com.allyoucanexercise.back_end.workout;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public class Workout {
    private int id;
    @NotNull
    private Integer userId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    // timestamp:
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/Timestamp.html

    public Workout() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public int getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
