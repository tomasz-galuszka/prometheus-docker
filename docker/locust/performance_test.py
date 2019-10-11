from locust import HttpLocust, TaskSet, task

class UserBehavior(TaskSet):

    @task(100)
    def hello(self):
        self.client.get("/actuator/health")


class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000