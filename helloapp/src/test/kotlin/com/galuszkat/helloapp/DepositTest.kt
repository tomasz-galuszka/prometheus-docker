package com.galuszkat.helloapp

import com.galuszkat.helloapp.core.Account
import com.galuszkat.helloapp.web.AccountRequest
import com.galuszkat.helloapp.web.DepositRequest
import com.galuszkat.helloapp.web.TransferRequest
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

  @Test
  fun transfer() {
    val source = createAccount()
    val destination = createAccount()

    val deferred = (1..10).map { _ ->
      GlobalScope.async {
        makeTransfer(10L, source, destination)
      }
    }
    Thread.sleep(20000)

    val sourceAmount = client.getForEntity<Account>("/accounts/{number}", source!!, Account::class).body?.amount
    val destinationAmount = client.getForEntity<Account>("/accounts/{number}", destination!!, Account::class).body?.amount

    assertEquals(BigDecimal.valueOf(-90L).setScale(2), sourceAmount)
    assertEquals(BigDecimal.valueOf(90L).setScale(2), destinationAmount)
  }

  private fun makeDeposit(number: Long?, amount: Long) {
    client.put("/accounts/deposit", DepositRequest(number!!, BigDecimal.valueOf(amount)))
  }

  private fun makeWithdraw(number: Long?, amount: Long) {
    client.put("/accounts/withdraw", DepositRequest(number!!, BigDecimal.valueOf(amount)))
  }

  private fun makeTransfer(amount: Long, sourceAccount: Long?, destinationAccount: Long?) {
    client.put("/accounts/deposit", TransferRequest(BigDecimal.valueOf(amount), sourceAccount!!, destinationAccount!!))
  }

  private fun createAccount(): Long? {
    val account = AccountRequest("FTest", "LTest")
    val response = client.postForEntity<Account>("/accounts/create", account, Account::class.java).body
    return response?.number
  }
}