# Performance tests setup for spring-boot kotlin web application using Prometheus, Grafana and Locust

## BUILD && RUN
````
cd helloapp && ./gradlew clean build && cd ..
docker-compose up --build
````

## Docker compose
### - prometheus --> localhost:9090
### - grafana (credentials: admin/admin) --> localhost:3000
### - helloapp --> localhost:8080
### - locust --> localhost:8089
