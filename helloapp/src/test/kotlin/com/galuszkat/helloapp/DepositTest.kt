package com.galuszkat.helloapp

import com.galuszkat.helloapp.core.Account
import com.galuszkat.helloapp.web.AccountRequest
import com.galuszkat.helloapp.web.DepositRequest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class DepositTest {

  @Autowired
  lateinit var client: TestRestTemplate

  @Test
  fun deposit() {
    val number = createAccount()

    val deferred = (1..10).map { _ ->
      GlobalScope.async {
        makeDeposit(number, 10L)
      }
    }
    Thread.sleep(10000)

    val amount = client.getForEntity<Account>("/accounts/{number}", number!!, Account::class).body?.amount
    assertEquals(BigDecimal.valueOf(90L).setScale(2), amount)
  }

  @Test
  fun withdraw() {
    val number = createAccount()

    val deferred = (1..10).map { _ ->
      GlobalScope.async {
        makeWithdraw(number, 10L)
      }
    }
    Thread.sleep(10000)

    val amount = client.getForEntity<Account>("/accounts/{number}", number!!, Account::class).body?.amount
    assertEquals(BigDecimal.valueOf(-90L).setScale(2), amount)
  }

  private fun makeDeposit(number: Long?, amount: Long) {
    client.put("/accounts/deposit", DepositRequest(number!!, BigDecimal.valueOf(amount)))
  }

  private fun makeWithdraw(number: Long?, amount: Long) {
    client.put("/accounts/withdraw", DepositRequest(number!!, BigDecimal.valueOf(amount)))
  }

  private fun createAccount(): Long? {
    val account = AccountRequest("FTest", "LTest")
    val response = client.postForEntity<Account>("/accounts/create", account, Account::class.java).body
    return response?.number
  }
}