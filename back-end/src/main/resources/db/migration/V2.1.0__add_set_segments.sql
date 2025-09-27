ALTER TABLE exercise_set DROP COLUMN reps;
ALTER TABLE exercise_set DROP COLUMN weight;
ALTER TABLE exercise_set DROP COLUMN duration_seconds;
ALTER TABLE exercise_set DROP COLUMN distance_meters;
ALTER TABLE exercise_set DROP COLUMN distance_measurement;
ALTER TABLE exercise_set DROP COLUMN pace_per_mile;

CREATE TABLE IF NOT EXISTS set_segment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exercise_set_id BIGINT,
    segment_order INT NOT NULL,
    reps INT,
    weight DECIMAL(5,2) UNSIGNED,
    duration_seconds INT DEFAULT NULL,
    distance_meters DECIMAL(10,2) UNSIGNED DEFAULT NULL,
    distance_measurement ENUM('MILES', 'YARDS', 'METERS') DEFAULT NULL;
    pace_per_mile DECIMAL(5,2) UNSIGNED DEFAULT NULL;
    FOREIGN KEY (exercise_set_id) REFERENCES exercise_set(id)
);