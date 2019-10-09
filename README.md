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
