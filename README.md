Had to install Debugger for Java and Language Support for Java(TM) by Red Hat to be able to do. Run ->Run without debugging. Or can just do ./mvnw spring-boot:run


When doing curl commands need to put in the user name and password (password is printed in the console) i.e:
curl -u user:pass -X GET http://localhost:8080/api/exercises


curl -u user:pass -X POST http://localhost:8080/api/exercises -H "Content-Type: application/json" -d '{"id": 3, "name": "pull-up", "group": "UPPERBODY", "description": "do a pull-up"}'


curl -u user:pass -X POST http://localhost:8080/api/exercises -H "Content-Type: application/json" -d '{"id": 4, "name": "squat", "group": "LOWERBODY", "description": "Squat till you drop"}'

its not working with the security dependency, so for now I comment that out when I build, then I run the above commands without the userpass input. i.e: curl -X GET http://localhost:8080/api/exercises