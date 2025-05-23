package com.allyoucanexercise.back_end.workout;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.allyoucanexercise.back_end.user.User;

@Entity
@Table(name = "workout")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @Column(name = "workout_notes")
    private String workoutNotes;

    protected Workout() {
    }

    public Workout(User user, String title, LocalDateTime completedAt, String workoutNotes) {
        this.user = user;
        this.title = title;
        this.completedAt = completedAt;
        this.workoutNotes = workoutNotes;
    }

    public Workout(User user, String title, LocalDateTime createdAt, LocalDateTime completedAt, String workoutNotes) {
        this.user = user;
        this.title = title;
        this.completedAt = completedAt;
        this.workoutNotes = workoutNotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return user.getId();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getWorkoutNotes() {
        return this.workoutNotes;
    }

    public void setWorkoutNotes(String workoutNotes) {
        this.workoutNotes = workoutNotes;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "user='" + user + '\'' +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", completedAt=" + completedAt +
                ", workoutNotes='" + workoutNotes + '\'' +
                '}';
    }
}
