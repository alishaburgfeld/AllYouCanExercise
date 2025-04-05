CREATE TABLE IF NOT EXISTS exercise (
   id INT NOT NULL AUTO_INCREMENT,
   name varchar(250) NOT NULL,
   exercise_group ENUM('SHOULDERS','CHEST','FOREARMS','OBLIQUES','QUADS','ADDUCTORS','ABS','BICEPS','CARDIO','TRAPS','TRICEPS','ABDUCTORS','HAMSTRINGS','CALVES','LATS','LOWER_BACK','GLUTES') NOT NULL,
   exercise_type ENUM('UPPERBODY', 'LOWERBODY', 'CARDIO', 'ABS') NOT NULL,
   description TEXT NOT NULL,
   picture varchar(600),
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS equipment (
   id INT NOT NULL AUTO_INCREMENT,
   name ENUM('DUMBBELLS','BARBELL','FULL_GYM','WEIGHT_BENCH','NONE') NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exercise_equipment (
   exercise_id INT NOT NULL,
   equipment_id INT NOT NULL,
   CONSTRAINT fk_exercise FOREIGN KEY (exercise_id) REFERENCES exercise(id) ON UPDATE CASCADE ON DELETE CASCADE,
   CONSTRAINT fk_equipment FOREIGN KEY (equipment_id) REFERENCES equipment(id) ON UPDATE CASCADE ON DELETE CASCADE 
);

CREATE TABLE IF NOT EXISTS user (
   id INT NOT NULL AUTO_INCREMENT,
   username VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS workout (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE workout_exercise (
    id INT AUTO_INCREMENT PRIMARY KEY,
    workout_id INT,
    exercise_id INT,
    exercise_order INT,
    FOREIGN KEY (workout_id) REFERENCES workout(id),
    FOREIGN KEY (exercise_id) REFERENCES exercise(id)
);

CREATE TABLE exercise_set (
    id INT AUTO_INCREMENT PRIMARY KEY,
    workout_exercise_id INT,
    set_order INT NOT NULL,
    reps INT,
    weight DECIMAL(5,2) UNSIGNED, -- supports decimal weights like 95.5
    FOREIGN KEY (workout_exercise_id) REFERENCES workout_exercise(id)
);