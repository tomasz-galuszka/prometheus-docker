package com.galuszkat.helloapp.core

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.LockModeType


@Repository
interface AccountRepository : JpaRepository<Account, Long> {

  @Query("SELECT max(a.number) from Account a")
  fun selectLastAccountNumber(): Optional<Long>

  @Lock(LockModeType.PESSIMISTIC_READ)
  override fun findById(id: Long): Optional<Account>
}