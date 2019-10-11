FROM python:2

RUN pip install locustio

COPY ./performance_test.py locustfile.py

EXPOSE 8089

ENTRYPOINT ["locust"]

CMD ["--host=http://helloapp:8080"]
