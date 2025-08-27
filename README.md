<!-- -------------------Decision Notes --------------------->

1. Had to install Debugger for Java and Language Support for Java(TM) by Red Hat to be able to do. Run ->Run without debugging. Or can just do ./mvnw spring-boot:run
   -this is not working because I don't have java installed, and I wasn't in the back-end folder.

2. CSRF wasn't working b/c when working with a javascript frontend you have to force your spring security to refresh the token every request. Thats why I had to add the SpaCsrfTokenRequestHandler. Here is where I learned about that: https://stackoverflow.com/questions/74447118/csrf-protection-not-working-with-spring-security-6

3. I was running into a bunch of null pointer exceptions trying to allow the datefiles for completedAt and createdAt to be null. I'm going to change my database to require a completedat and createdat time. The front end will either allow the user to input a date-time, and if they don't set the time to now. i.e
   <input
   type="datetime-local"
   </input>

My java will use import java.time.LocalDateTime;
My sql will use completedAt DATETIME

4. This is the commit with all the security config to allow all subsequent post calls:
   0874023
   https://github.com/alishaburgfeld/AllYouCanExercise/commit/d774bc7a107b9db5f9e01f83ec209cf30f900048
5. Used Nginx config to serve the front-end files. The "step 2" in my front-end dockerfile will compile all of my react files into just an index.html file. So if I want to see that all my react files are being copied over like I want, comment out the step 2 nginx stuff then exec into the container. However when I build the nginx container then all I should see is an assets folder (with all my images) and an index.html file. These are located inside /usr/share/nginx/html

6. added flyaway to do migration scripts - look for this message in the logs after a migration:
   Flyway Community Edition ...
   Successfully applied 1 migration to schema `exercise-database` (execution time 00:01)

<!-- -------------------REST --------------------->

When have security dependency invoked it prints a password in the console to connect to the localhost:
https://stackoverflow.com/questions/37285016/what-is-username-and-password-when-starting-spring-boot-with-tomcat

When doing curl commands need to put in the user name and password (password is printed in the console) i.e:
curl -u user:pass -X GET http://localhost:8080/api/exercises

curl -u user:pass -X POST http://localhost:8080/api/exercises -H "Content-Type: application/json" -d '{"id": 3, "name": "pull-up", "exercise_type": "UPPERBODY", "description": "do a pull-up"}'

curl -u user:pass -X POST http://localhost:8080/api/exercises -H "Content-Type: application/json" -d '{"id": 4, "name": "squat", "exercise_type": "LOWERBODY", "description": "Squat till you drop"}'

its not working with the security dependency, so for now I comment that out when I build, then I run the above commands without the userpass input. i.e: curl -X GET http://localhost:8080/api/exercises

<!-- Database -->

# exec into the container: docker exec -it allyoucanexercise-mysql-1 sh

docker exec -it allyoucanexercise-mysql sh

<!-- After I dockerized it became allyoucanexercise-mysql sh
Also other containers are: docker exec -it allyoucanexercise-frontend-1 sh -->

<!-- cd /usr/share/nginx/html -->

# then run this to connect to mysql: mysql -u alisha -psecret

# then show databases

# use exercise-database

# show tables

<!-- if need to drop tables: follow this order:
drop table exercise_set;
drop table workout_exercise;
drop table workout;
drop table user;
drop table exercise_equipment;
drop table equipment;
drop table exercise;

<!-- App Information -->

Start Docker: Open Docker Desktop on mac -- possible way to hide the desktop, try this next time: https://stackoverflow.com/questions/64533789/how-to-start-docker-desktop-with-a-cli-command-on-macos-without-showing-dashboar

Start backend - go to a .java file, click run, run without debugging

To start the front-end: cd into front-end and type npm start go to localhost:3000 to run tests: npm run test ( need to be in the react folder)

To run application:

Start Docker: Open Docker Desktop on mac -- possible way to hide the desktop, try this next time: https://stackoverflow.com/questions/64533789/how-to-start-docker-desktop-with-a-cli-command-on-macos-without-showing-dashboar

<!-- DOCKER DOCKER DOCKER -->

If I need to delete my persistent volume (mysql data) on my ec2 I need to run:
If you had used docker compose down -v → that would delete named volumes too, including MySQL data.

To build just one of the containers (i.e react) cd into front-end then build the image: docker build -t <whatever-name-you-want> .

to then run and exec into the container run:
docker run -it --rm <whatever-name-you-made> sh

Build both images and container: docker-compose up --build
get docker image id: docker images
exec into your docker container: docker exec -it exercise_app-exercise-react-app-1 sh
./mvnw spring-boot:run

If I want to remove all containers: docker rm -vf $(docker ps -aq)
THEN if I want to remove all images: docker rmi -f $(docker images -aq)

Material UI Site: https://mui.com/material-ui/getting-started/supported-components/

sx props: https://mui.com/system/getting-started/the-sx-prop/#spacing

Material UI tutorials: https://www.youtube.com/watch?v=h9KevTtI5O0&list=PLDxCaNaYIuUlG5ZqoQzFE27CUOoQvOqnQ&index=1

<!-- Things I have learned: -->

Docker: -to delete docker image = docker rmi -to delete docker containers: docker compose down -to delete docker container manually = first stop it. docker stop then docker rm -to see all containers (even stopped ones) = docker ps -a -to see logs: Docker container logs lb -“Dockerfile” is the instructions for building a container image. https://www.youtube.com/watch?v=LQjaJINkQXY

-docker cheatsheet: https://www.javainuse.com/devOps/docker/docker-commands-cheat-sheet

VITE Environment variables:
All environment variables intended for client-side access in a Vite application must be prefixed with VITE\_. For example, VITE_API_KEY=your_key_here
Environment variables are accessed in the code using import.meta.env. For example: import.meta.env.VITE_API_KEY

These are also baked-in at build-time - so when I was attempting to build it locally and then copy the dist folder over, it was not working b/c it was copying the local env variables - i also had to add the arg into the dockerfile and compose.yaml.

The trailing / at the end of proxy_pass replaces the URI path.

For example, a request to /api/exercises/group/CARDIO gets forwarded to http://backend:8080/exercises/group/CARDIO → Spring sees /exercises/... instead of /api/exercises/....

If your controllers are mapped with @RequestMapping("/api"), Spring will not match the path, so it defaults to static resource handling → 403/404.

The reason running "run without debugging" vs the actual java jar command was working is because:
1 - I needed to build the mysql container first
2 - Spring is literally seeing the string ${SPRING_DATASOURCE_URL} instead of a real JDBC URL. That means your environment variable is not being injected into the JAR. If you just run java -jar from the shell without exporting the datasource environment variables (in application.properties), Spring cannot substitute them. VS Code “Run Without Debugging” works because VS Code sets these environment variables automatically from your .env or launch config. So I had to pass them directly into the java jar command example:
java -jar target/app.jar \
--spring.datasource.url=jdbc:<> \
--spring.datasource.username=<> \
--spring.datasource.password=<>

I also had to add the vite react variable to the front-end .env file so that when I do npm run dev it will grab the variable from that directory.

functional java methods:
To avoid repetitive if blocks when updating fields in an ExerciseRecord, Java functional interfaces can be used to pass in getters and setters as parameters. This allows for a single, reusable method to handle conditional field updates. The key interfaces used are: Supplier<T>, which represents a function that returns a value and takes no arguments (used to get a field value, e.g., existingExerciseRecord::getMaxReps); and BiConsumer<T, U>, which represents a function that takes two arguments and returns nothing (used to set a value or reference, e.g., ExerciseRecord::setMaxReps or ExerciseRecord::setMaxRepsWorkout). A generic method can be defined like this:
private <T extends Number & Comparable<T>> void updateRecordIfWorkoutValueIsHigher(
T workoutValue,
Supplier<T> getExistingValue,
BiConsumer<ExerciseRecord, T> setNewValue,
BiConsumer<ExerciseRecord, Workout> setWorkout,
ExerciseRecord record,
Workout workout) {

    T existingValue = getExistingValue.get();
    if (workoutValue != null && existingValue != null && workoutValue.compareTo(existingValue) > 0) {
        setNewValue.accept(record, workoutValue);
        setWorkout.accept(record, workout);
    }

}
This method compares the workoutValue to the existing value in the record using compareTo(). If it's higher, it updates the record with the new value and associates it with the given workout. This approach improves readability, reduces duplication, and makes the update logic extensible for any Number type like Integer or Float.

<!-- Helpful Videos and Tutorials-->

Create React App: -https://www.freecodecamp.org/news/how-to-build-a-react-project-with-create-react-app-in-10-steps/

Integrating React with Spring Boot: -https://stackoverflow.com/questions/69511990/how-to-integrate-spring-boot-with-react

springboot tutorial: https://spring.io/guides/gs/spring-boot

deploying a hello world in kubernetes: https://www.youtube.com/watch?v=XltFOyGanYE

this will be helpful when setting up my initial mysql database and table: docker + mysql + docker: https://www.javainuse.com/devOps/docker/docker-mysql

springboot + java + minikub k8s: https://www.youtube.com/watch?v=cvi3pMelCV0 https://www.youtube.com/watch?v=0GgBi8yNQT4

https://docs.oracle.com/javase/tutorial/reallybigindex.html

React/Java Fitness:

1. https://www.youtube.com/watch?v=gpqoZQ8GNK8
2. https://www.youtube.com/watch?v=KBpoBc98BwM
   --the second one uses apis to grab videos and exercise descriptions

<!-- Helpful Articles/Information -->

-which dockerfile to use? https://www.techtarget.com/searchitoperations/tip/Choose-the-best-Docker-image-for-the-job-at-hand

-Info on what to copy into container: https://www.phind.com/search?cache=ahstmilr6cvk07axos43g6jk

-Maven Repository: https://mvnrepository.com/

SpringBoot: -In Spring Boot, port 8080 is the default port that the embedded Tomcat server uses to serve web applications. When you run a Spring Boot application without specifying a port, it automatically starts on port 8080 by default

How to handle CSRF configuration: https://chatgpt.com/share/67992954-c8e0-800f-b2a5-03943e2854cc

<!----------------- TODOS ----------->

1. M ake a wider navbar on big screens
2. Figure out how to grab all the core exercises AND any custom user created exercises. (not allowing custom exercises for now)
3. Verify the way I'm doing CORS exception is a secure way
4. Database variables for mysql container
5. context load - probably not working because security dependency is disabled.
6. Look into using CreateContext to store react user data so I don't have to keep passing user to all my pages.
7. Need to add logic to logout to reset the active workout. Also have a problem with the sets/reps showing. Also alert the user when they've added an exercise to a workout, and alert them if they're trying to add one that is already in the workout.
   https://dev.to/jucheng925/react-persisting-data-on-page-refresh-1jhk
8. Refactor session setting/getting on my react pages:
   // a way to potentially refactor these session states into one function:
   // search for "custom hook" https://chatgpt.com/share/67f3d8fb-12f8-800f-9475-560f78c153f4

9. need to figure out how I want to view my workouts and exercises. also, do I want to clear the active workout upon saving the workout?
10. Create deployment script

May need to read this with testing with MUI Material UI:
https://jskim1991.medium.com/react-dont-give-up-on-testing-when-using-material-ui-with-react-ff737969eec7

https://medium.com/@MussieTeshome/react-testing-library-with-vitest-the-basics-explained-d583e62945fd

to run test coverage:
npm run test -- --coverage --

<!-- to do: watch this video on user registration and login -->

https://www.youtube.com/watch?v=X7pGCmVxx10

look into:
https://chatgpt.com/share/67e70d05-6fe8-800f-a14d-4afaccf0600d

I need to change my current user setup because it is not setting a session for the user.
https://chatgpt.com/share/67e70d05-6fe8-800f-a14d-4afaccf0600d

<!-- JPA Repository -->

https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html

Full list of queries:
https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html#appendix.query.method.subject

https://docs.spring.io/spring-data/jpa/docs/1.5.1.RELEASE/reference/html/jpa.repositories.html#jpa.sample-app.finders.strategies

CRUD functions:
https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html

<!-- getting a bunch of these errors:
2025-04-19T10:39:45.141-04:00  WARN 13476 --- [back-end] [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Thread starvation or clock leap detected (housekeeper delta=2m7s845ms).
2025-04-19T10:40:32.889-04:00  WARN 13476 --- [back-end] [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Thread starvation or clock leap detected (housekeeper delta=47s748ms).
2025-04-19T10:56:31.335-04:00  WARN 13476 --- [back-end] [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Thread starvation or clock leap detected (housekeeper delta=15m28s431ms). -->

<!-- setting up secrets and deploying: -->

https://chatgpt.com/share/6840ef16-d8c4-800f-8648-ff8f17d04206

Connect to my ec2 instance:
cd Documents/AllYouCanExercise
ssh -i all-you-can-exercise-key-pair.pem ec2-user@44.208.25.2

sudo su (puts you in sudo so you don't have to write sudo in front of all the following commands)

   <!-- 
    single command to use:
    scp -i all-you-can-exercise-key-pair.pem \
    ./front-end/nginx.conf \
    ec2-user@44.208.25.2:/home/ec2-user/front-end -->

-d does it in detached mode, if I need to see the logs because something is not working then remove the -d.

one time steps I took:
install docker-compose:https://stackoverflow.com/questions/63708035/installing-docker-compose-on-amazon-ec2-linux-2-9kb-docker-compose-file

docker stop all containers: docker stop $(docker ps -q)

# start ec2

1. cd Documents/AllYouCanExercise
2. ssh -i all-you-can-exercise-key-pair.pem ec2-user@44.208.25.2
3. sudo su
4. service docker start
5. docker-compose up --build OR docker-compose up --build -d

<!-- steps to take when having made front-end changes -->

# Remove front-end folder in ec2:

rm -rf front-end

<!-- # build front-end build on vscode:

cd front-end
npm run build
cd .. -->

# copy over all front-end files on vscode:

If I don't have front-end and src and public folders yet:
ssh -i all-you-can-exercise-key-pair.pem ec2-user@44.208.25.2 "mkdir -p /home/ec2-user/front-end/src"

ssh -i all-you-can-exercise-key-pair.pem ec2-user@44.208.25.2 "mkdir -p /home/ec2-user/front-end/public"

scp -i all-you-can-exercise-key-pair.pem \
 -r ./front-end/src \
 ec2-user@44.208.25.2:/home/ec2-user/front-end/

scp -i all-you-can-exercise-key-pair.pem \
 ./front-end/package.json \
 ./front-end/package-lock.json \
 ./front-end/nginx.conf \
 ./front-end/Dockerfile \
 ./front-end/index.html \
 ./front-end/vite.config.js \
 ec2-user@44.208.25.2:/home/ec2-user/front-end

 <!-- scp -i all-you-can-exercise-key-pair.pem \
 ./front-end/nginx.conf \
 ec2-user@44.208.25.2:/home/ec2-user/front-end -->

scp -i all-you-can-exercise-key-pair.pem \
 -r ./front-end/public \
 ec2-user@44.208.25.2:/home/ec2-user/front-end

<!-- steps to take when having made root level compose.yml/.env files: -->

# Remove current .env, compose.yml inside ec2:

rm -rf .env
rm -rf docker-compose.yml

# copy over new files from vscode:

scp -i all-you-can-exercise-key-pair.pem \
 ./.env.production \
 ec2-user@44.208.25.2:/home/ec2-user

scp -i all-you-can-exercise-key-pair.pem \
 ./docker-compose.prod.yml \
 ec2-user@44.208.25.2:/home/ec2-user

# rename production files inside ec2:

mv docker-compose.prod.yml docker-compose.yml
mv .env.production .env

<!-- steps to take when made backend changes: -->

# remove existing files on ec2:

rm -rf ./back-end/target
rm -rf ./back-end/Dockerfile

# copy the jar and the dockerfile within vscode:

1. run in back-end in vscode: mvn clean package -DskipTests

If I don't have back-end and target folders yet: ssh -i all-you-can-exercise-key-pair.pem ec2-user@44.208.25.2 "mkdir -p /home/ec2-user/back-end/target"

scp -i all-you-can-exercise-key-pair.pem \
 ./back-end/Dockerfile \
 ec2-user@44.208.25.2:/home/ec2-user/back-end/

scp -i all-you-can-exercise-key-pair.pem \
 ./back-end/target/back-end-0.0.1-SNAPSHOT.jar \
 ec2-user@44.208.25.2:/home/ec2-user/back-end/target/

<!-- its not building my secrets at run-time. ask chat gpt for a smooth dockerfile, env, nginx.conf, compose.yml set up - send all files -->

# local workflow w/o using docker:

cd mysql
docker compose -f docker-compose.mysql.yml up -d
cd ../back-end
./mvnw clean package -DskipTests

java -jar target/back-end-0.0.1-SNAPSHOT.jar \
--spring.datasource.url=jdbc:mysql://localhost:3306/exercise-database \
--spring.datasource.username=alisha \
--spring.datasource.password=secret

cd ../front-end
npm install (if necessary)
npm run dev

# Dockerized local development: (not recommended b/c won't do instant changes for front-end)

cd back-end
./mvnw clean package -DskipTests
cd ..
docker compose up --build
docker compose logs -f

# Migrations

Re-build jar and copy it over
In ec2 : docker-compose down
docker-compose up --build

<!-- should run the scripts automatically -->

pace_per_mile = (duration_in_seconds / 60) / (distance_in_meters / 1609.34)
