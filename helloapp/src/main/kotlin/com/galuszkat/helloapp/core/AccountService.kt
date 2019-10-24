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
    logger.info("--- Start fetching account .....")
    val account = this.repository.findByNumberLock(number).orElseThrow { RuntimeException("Account not found") }

    logger.info("--- Account fetched, deposit: {}", account.amount)
    Thread.sleep(1000)

    val newAmount = account.amount + amount
    val updatedAccount = account.copy(amount = newAmount)
    this.repository.save(updatedAccount)

    logger.info("--- Updated account, deposit: {}", updatedAccount.amount)

    return updatedAccount
  }

  @Transactional
  fun withdraw(amount: BigDecimal, number: Long): Account {
    logger.info("--- Start fetching account .....")
    val account = this.repository.findByNumberLock(number).orElseThrow { RuntimeException("Account not found") }

    logger.info("--- Account fetched, deposit: {}", account.amount)
    Thread.sleep(1000)

    val newAmount = account.amount - amount
    val updatedAccount = account.copy(amount = newAmount)
    this.repository.save(updatedAccount)

    logger.info("--- Updated account, deposit: {}", updatedAccount.amount)

    return updatedAccount
  }

  fun get(number: Long): Account {
    return repository.findById(number).orElseThrow { java.lang.RuntimeException("Account not found") }
  }

}