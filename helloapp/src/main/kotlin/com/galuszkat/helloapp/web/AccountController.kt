package com.galuszkat.helloapp.web

import com.galuszkat.helloapp.core.Account
import com.galuszkat.helloapp.core.AccountService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid
@RestController
@RequestMapping("/accounts")
class AccountController(
    private val service: AccountService
) {

    private val logger = LoggerFactory.getLogger(AccountController::class.java)

    @GetMapping("/")
    fun list(): List<Account> {
        return service.list(200).get().collect(Collectors.toList())
    }

    @GetMapping("/{number}")
    fun get(@PathVariable number: Long): Account {
        logger.info("get for: {}", number)

        return service.get(number)
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody request: AccountRequest): Account {
        logger.info("Create for: {}", request)

        return service.create(request.toDomain())
    }

    @PutMapping("/deposit")
    fun deposit(@Valid @RequestBody request: DepositRequest): Account {
      return service.deposit(request.amount, request.number)
    }
}