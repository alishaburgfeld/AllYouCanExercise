CREATE TABLE IF NOT EXISTS exercise (
   id INT NOT NULL,
   name varchar(250) NOT NULL,
   -- exercise_type varchar(10) NOT NULL,
   exercise_group ENUM('SHOULDERS','CHEST','FOREARMS','OBLIQUES','QUADS','ADDUCTORS','ABS','BICEPS','CARDIO','TRAPS','TRICEPS','ABDUCTORS','HAMSTRINGS','CALVES','LATS','LOWER_BACK','GLUTES') NOT NULL,
   exercise_type ENUM('UPPERBODY', 'LOWERBODY', 'CARDIO', 'ABS') NOT NULL,
   description TEXT NOT NULL,
   PRIMARY KEY (id)
);