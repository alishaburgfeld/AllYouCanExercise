CREATE TABLE IF NOT EXISTS exercise (
   id INT NOT NULL,
   name varchar(250) NOT NULL,
   exercise_type varchar(10) NOT NULL,
   description varchar(250) NOT NULL,
   PRIMARY KEY (id)
);