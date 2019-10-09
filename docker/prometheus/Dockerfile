FROM ubuntu:18.04

# Install WGET
RUN  apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# Download prometheus
RUN cd /opt && \
  wget https://github.com/prometheus/prometheus/releases/download/v2.13.0/prometheus-2.13.0.linux-amd64.tar.gz && \
  tar -zxvf prometheus-2.13.0.linux-amd64.tar.gz && \
  mv prometheus-2.13.0.linux-amd64 prometheus

# Configure prometheus
COPY ./prometheus.yml /opt/prometheus/prometheus.yml

# Clean up
RUN cd /opt && \
  rm prometheus-2.13.0.linux-amd64.tar.gz

EXPOSE 9090/tcp

ENTRYPOINT ["/opt/prometheus/prometheus"]
CMD ["--config.file=/opt/prometheus/prometheus.yml"]