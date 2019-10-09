# prometheus-docker

## BUILD
````
docker build -t galuszkat/prometheus .
````

## RUN
````
docker run -p 9090:9090 galuszkat/prometheus
````

# node_exported-docker

## BUILD
````
docker build -t galuszkat/node_exported -f DockerfileNodeExporter .
````

## RUN
````
docker run -p 9100:9100 galuszkat/node_exported
````

# hello app

## BUILD
````
cd helloapp
./gradlew clean build
docker build --build-arg JAR_FILE=./helloapp/build/libs/helloapp-0.0.1-SNAPSHOT.jar -t galuszkat/helloapp -f DockerfileHelloApp .
````
## RUN
````
docker run -p 8080:8080 galuszkat/helloapp
````

## Docker compose
### - prometheus
### - node_exporter
### - grafana (admin/admin)
### - helloapp
