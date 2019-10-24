package com.galuszkat.helloapp.core

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class AccountService(
    private val repository: AccountRepository
) {

  private val FIRST_NUMBER = 1000000000L
  private val logger = LoggerFactory.getLogger(AccountService::class.java)

  fun list(limit: Int): Page<Account> {
    val pageAndSort = PageRequest.of(0, 200, Sort.by("amount").descending())
    return repository.findAll(pageAndSort)
  }

  fun create(data: Owner): Account {
    val number = this.repository.selectLastAccountNumber().orElse(FIRST_NUMBER) + 1
    val account = Account(number, data, BigDecimal.ZERO, LocalDateTime.now())

    return this.repository.save(account)
  }

  @Transactional
  fun deposit(amount: BigDecimal, number: Long): Account {
    val account = this.getAndLock(number)

    sleep()

    val newAmount = account.amount + amount

    return updateAccountAmount(account, newAmount)
  }

  @Transactional
  fun withdraw(amount: BigDecimal, number: Long): Account {
    val account = this.getAndLock(number)

    sleep()

    val newAmount = account.amount - amount

    return updateAccountAmount(account, newAmount)
  }

  @Transactional
  fun transfer(amount:BigDecimal, fromNumber: Long, toNumber: Long): Account {
    if (fromNumber == toNumber) {
      throw RuntimeException("Same accounts")
    }

    val sourceAccount: Account
    val destinationAccount: Account

    if (compareValues(fromNumber, toNumber) > 0) {
      sourceAccount = this.getAndLock(fromNumber)
      destinationAccount = this.getAndLock(toNumber)
    } else {
      destinationAccount = this.getAndLock(toNumber)
      sourceAccount = this.getAndLock(fromNumber)
    }

    val sourceNewAmount = sourceAccount.amount - amount
    val destinationNewAmount = destinationAccount.amount + amount

    updateAccountAmount(destinationAccount, destinationNewAmount)
    val sourceAccountUpdated = updateAccountAmount(sourceAccount, sourceNewAmount)

    return sourceAccountUpdated
  }

  fun get(number: Long): Account {
    logger.info("--- Start fetching account .....")

    val account = repository.findById(number).orElseThrow { java.lang.RuntimeException("Account not found") }

    logger.info("--- Account fetched, deposit: {}", account.amount)
    return account
  }

  @Transactional
  fun getAndLock(number: Long): Account {
    logger.info("--- Start fetching account (LOCK) .....")

    val account = repository.findByNumberLock(number).orElseThrow { java.lang.RuntimeException("Account not found") }

    logger.info("--- Account fetched (LOCK), deposit: {}", account.amount)
    return account
  }

  private fun updateAccountAmount(account: Account, newAmount: BigDecimal): Account {
    val updatedAccount = account.copy(amount = newAmount)
    logger.info("--- Start updating account, deposit: {}", updatedAccount.amount)

    val result = this.repository.save(updatedAccount)

    logger.info("--- Updated account, deposit: {}", result.amount)
    return result
  }

  private fun sleep() {
    Thread.sleep(1000)
  }

}