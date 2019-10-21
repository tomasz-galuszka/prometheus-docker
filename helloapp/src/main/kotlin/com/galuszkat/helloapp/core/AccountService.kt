package com.galuszkat.helloapp.core

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class AccountService(
    private val repository: AccountRepository
) {

  private val FIRST_NUMBER = 1000000000L

  fun list(limit: Int): Page<Account> {
    val pageAndSort = PageRequest.of(0, 200, Sort.by("amount").descending())
    return repository.findAll(pageAndSort)
  }

  fun create(data: Owner): Account {

    val number = this.repository.selectLastAccountNumber().orElse(FIRST_NUMBER) + 1

    val account = Account(number, data, BigDecimal.ZERO, LocalDateTime.now())

    return this.repository.save(account)
  }

}