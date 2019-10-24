from locust import HttpLocust, TaskSet, task
import random

class CreateAccountTest(TaskSet):

    @task(10)
    def list(self):
        self.client.get("/accounts")

    @task(1)
    def createAccount(self):
        self.client.post("/accounts/create", json = {
            "firstName": "Adam",
            "lastName": "testLast"
        })

class MakeDepositTest(TaskSet):

    def on_start(self):
        response = self.client.post("/accounts/create", json = {
            "firstName": "testFirst",
            "lastName": "testLast"
        }).json()
        self.number = response["number"]

    @task(10)
    def list(self):
        self.client.get("/accounts")

    @task(1)
    def makeDeposit(self):
        self.client.put("/accounts/deposit", json = {
            "number": self.number,
            "amount": 10
        })

class MakeWithdrawTest(TaskSet):

    def on_start(self):
        response = self.client.post("/accounts/create", json = {
            "firstName": "testFirst",
            "lastName": "testLast"
        }).json()
        self.number = response["number"]

    @task(10)
    def list(self):
        self.client.get("/accounts")

    @task(1)
    def makeDeposit(self):
        self.client.put("/accounts/withdraw", json = {
            "number": self.number,
            "amount": 10
        })

class MakeTransferTest(TaskSet):

    def on_start(self):
        response = self.client.post("/accounts/create", json = {
            "firstName": "testFirst",
            "lastName": "testLast"
        }).json()

        response2 = self.client.post("/accounts/create", json = {
            "firstName": "testFirst2",
            "lastName": "testLast2"
        }).json()

        self.sourceNumber = response["number"]
        self.destinationNumber = response2["number"]

    @task(10)
    def list(self):
        self.client.get("/accounts")

    @task(1)
    def makeDeposit(self):
        self.client.post("/accounts/transfer", json = {
            "sourceNumber": self.sourceNumber,
            "destinationNumber": self.destinationNumber,
            "amount": 10
        })

class CreateAccount(HttpLocust):
    task_set = CreateAccountTest

class MakeDeposit(HttpLocust):
    task_set = MakeDepositTest

class MakeWithdraw(HttpLocust):
    task_set = MakeWithdrawTest

class MakeTransfer(HttpLocust):
    task_set = MakeTransferTest