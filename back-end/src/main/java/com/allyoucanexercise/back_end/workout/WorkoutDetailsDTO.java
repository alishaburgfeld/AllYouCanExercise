package com.allyoucanexercise.back_end.workout;

import java.time.LocalDateTime;

public class WorkoutDetailsDTO {
    private String username;
    private String title;
    private LocalDateTime completedAt;
    private String workoutNotes;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getWorkoutNotes() {
        return workoutNotes;
    }

    public void setWorkoutNotes(String workoutNotes) {
        this.workoutNotes = workoutNotes;
    }

    @Override
    public String toString() {
        return "WorkoutDetailsDTO{" +
                "username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", completedAt=" + completedAt +
                ", workoutNotes='" + workoutNotes + '\'' +
                '}';
    }

}
