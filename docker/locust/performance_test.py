from locust import HttpLocust, TaskSet, task

class HelloAppPerformanceTest(TaskSet):

    @task
    def hello(self):
        self.client.get("/")


class WebsiteUser(HttpLocust):
    task_set = HelloAppPerformanceTest
