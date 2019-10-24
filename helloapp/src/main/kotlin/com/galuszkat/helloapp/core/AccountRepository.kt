package com.galuszkat.helloapp.core

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.LockModeType
import javax.persistence.QueryHint


@Repository
interface AccountRepository : JpaRepository<Account, Long> {

  @Query("SELECT max(a.number) from Account a")
  fun selectLastAccountNumber(): Optional<Long>

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints(value = [QueryHint(name = "javax.persistence.lock.timeout", value = "3000")])
  @Query("select a from Account a WHERE a.number = ?1")
  fun findByNumberLock(number: Long): Optional<Account>
}