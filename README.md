# estores
Seetharaman V's estores

Starting the application using gradle:
```$ ./gradlew clean build && java -jar build/libs/estores-0.0.1-SNAPSHOT.jar```

Dockerize the Applications [Mac has issues]:
```$ docker build -f Dockerfile -t estores .```
```$ docker image ls```
```$ docker ps```
```$ docker inspect <container_id>```
```$ docker run -p 9090:9090 estores```

DB Login
`````http://localhost:9090/estores/h2-console`````

Swagger 
`````http://localhost:9090/estores/swagger-ui.html`````
Actuator 
`````http://localhost:9090/estores/actuator`````
Metrics 
`````http://localhost:9090/estores/actuator/metrics`````
Prometheus
`````http://localhost:9090/estores/actuator/prometheus`````
