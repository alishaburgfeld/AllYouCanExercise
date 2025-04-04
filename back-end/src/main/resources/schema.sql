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