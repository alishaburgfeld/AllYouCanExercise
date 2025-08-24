ALTER TABLE exercise_set ADD COLUMN distance_measurement VARCHAR(250) DEFAULT NULL;
ALTER TABLE exercise_set ADD COLUMN pace_seconds INT DEFAULT NULL;

CREATE TABLE IF NOT EXISTS exercise_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exercise_id BIGINT,
    user_id BIGINT,
    last_exercised DATETIME,
    max_sets INT,
    max_sets_workout_id BIGINT,
    max_reps INT,
    max_reps_workout_id BIGINT,
    max_weight DECIMAL(5,2) UNSIGNED,
    max_weight_workout_id BIGINT,
    max_volume DECIMAL(5,2) UNSIGNED,
    max_volume_workout_id BIGINT,
    max_duration_seconds INT DEFAULT NULL, -- put it a default null since most workouts won't use a time. only cardio. ie 930 = 15 minutes
    max_duration_workout_id BIGINT,
    max_distance_meters DECIMAL(10,2) UNSIGNED DEFAULT NULL, -- put it a default null since most workouts won't use a distance
    max_distance_workout_id BIGINT,
    max_pace_seconds DECIMAL(10,2) UNSIGNED DEFAULT NULL, -- put it a default null since most workouts won't use a distance
    max_pace_workout_id BIGINT,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id),
    FOREIGN KEY (max_sets_workout_id) REFERENCES workout(id),
    FOREIGN KEY (max_reps_workout_id) REFERENCES workout(id),
    FOREIGN KEY (max_weight_workout_id) REFERENCES workout(id),
    FOREIGN KEY (max_volume_workout_id) REFERENCES workout(id),
    FOREIGN KEY (max_distance_workout_id) REFERENCES workout(id),
    FOREIGN KEY (max_duration_workout_id) REFERENCES workout(id),
    FOREIGN KEY (max_pace_workout_id) REFERENCES workout(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    UNIQUE KEY (user_id, exercise_id)
);