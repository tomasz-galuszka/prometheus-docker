package com.galuszkat.helloapp.core

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Long> {

  @Query("SELECT max(a.number) from Account a")
  fun selectLastAccountNumber(): Optional<Long>
}