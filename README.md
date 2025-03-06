<!-- -------------------Decision Notes --------------------->

1. Had to install Debugger for Java and Language Support for Java(TM) by Red Hat to be able to do. Run ->Run without debugging. Or can just do ./mvnw spring-boot:run
   -this is not working because I don't have java installed, and I wasn't in the back-end folder.

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

# then run this to connect to mysql: mysql -u alisha -psecret

# then show databases

# use <db name>

# show tables

<!-- App Information -->

minikube location: /opt/homebrew/bin/minikube

Start Docker: Open Docker Desktop on mac -- possible way to hide the desktop, try this next time: https://stackoverflow.com/questions/64533789/how-to-start-docker-desktop-with-a-cli-command-on-macos-without-showing-dashboar Start Minikube: minikube start --driver=docker

To start the front-end: cd into front-end and type npm start go to localhost:3000 to run tests: npm run test ( need to be in the react folder)

To run application:

Start Docker: Open Docker Desktop on mac -- possible way to hide the desktop, try this next time: https://stackoverflow.com/questions/64533789/how-to-start-docker-desktop-with-a-cli-command-on-macos-without-showing-dashboar

<!-- Start Minikube: minikube start --driver=docker -->

Build both images and container: docker-compose up --build
get docker image id: docker images
exec into your docker container: docker exec -it exercise_app-exercise-react-app-1 sh
./mvnw spring-boot:run

Material UI Site: https://mui.com/material-ui/getting-started/supported-components/

sx props: https://mui.com/system/getting-started/the-sx-prop/#spacing

Material UI tutorials: https://www.youtube.com/watch?v=h9KevTtI5O0&list=PLDxCaNaYIuUlG5ZqoQzFE27CUOoQvOqnQ&index=1

Things I have learned:

Docker: -to delete docker image = docker rmi -to delete docker containers: docker compose down -to delete docker container manually = first stop it. docker stop then docker rm -to see all containers (even stopped ones) = docker ps -a -to see logs: Docker container logs lb -“Dockerfile” is the instructions for building a container image. https://www.youtube.com/watch?v=LQjaJINkQXY

-docker cheatsheet: https://www.javainuse.com/devOps/docker/docker-commands-cheat-sheet

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

1. Add a navbar - look into how to do a hamburger style on tailwind
2. Figure out how I'm going to insert the Exercise names onto the main picture page, and how upon click it will go to the exercise page.
3. Make it so id is auto-populated based on the next value in the mysql database
4. Verify the way I'm doing CORS exception is a secure way
5. Database variables for mysql container
6. context load - probably not working because security dependency is disabled.
7. Instead of grabbing all the exercises, only grab the exercisegroup when clicking on the label

May need to read this with testing with MUI Material UI:
https://jskim1991.medium.com/react-dont-give-up-on-testing-when-using-material-ui-with-react-ff737969eec7

https://medium.com/@MussieTeshome/react-testing-library-with-vitest-the-basics-explained-d583e62945fd

to run test coverage:
npm run test -- --coverage --
