from locust import HttpLocust, TaskSet, task
import random

class HelloAppPerformanceTest(TaskSet):

    @task(10)
    def list(self):
        self.client.get("/users/list")

    @task(1)
    def register(self):
        self.client.post("/users/register", {
          "firstName", "test",
          "lastName", "xxx",
          "age", random.randint(18,150),
          "email", "test@xxx.com",
        })


class WebsiteUser(HttpLocust):
    task_set = HelloAppPerformanceTest
