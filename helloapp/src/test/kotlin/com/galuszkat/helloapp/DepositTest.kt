package com.galuszkat.helloapp

import com.galuszkat.helloapp.core.Account
import com.galuszkat.helloapp.web.AccountRequest
import com.galuszkat.helloapp.web.DepositRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class DepositTest {

  @Autowired
  lateinit var client: TestRestTemplate



  @Test
  fun test() {
    val account = AccountRequest("FTest", "LTest")
    val response = client.postForEntity<Account>("/accounts/create", account, Account::class.java).body
    val number = response?.number

    val deposit = DepositRequest(number!!, BigDecimal.valueOf(10L))

    client.put("/accounts/deposit", deposit)

    val updatedAccount = client.getForEntity<Account>("/accounts/{number}", number, Account::class).body

    println(updatedAccount)

  }
}