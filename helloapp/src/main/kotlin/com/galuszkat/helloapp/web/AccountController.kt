package com.galuszkat.helloapp.web

import com.galuszkat.helloapp.core.Account
import com.galuszkat.helloapp.core.AccountService
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val service: AccountService
) {

    @GetMapping("/")
    fun list(): List<Account> {
        return service.list(200).get().collect(Collectors.toList())
    }

    @GetMapping("/{number}")
    fun get(@PathVariable number: Long): Account {
        return service.get(number)
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody request: AccountRequest): Account {
        return service.create(request.toDomain())
    }

    @PutMapping("/deposit")
    fun deposit(@Valid @RequestBody request: DepositRequest): Account {
      return service.deposit(request.amount, request.number)
    }

    @PutMapping("/withdraw")
    fun withdraw(@Valid @RequestBody request: DepositRequest): Account {
        return service.withdraw(request.amount, request.number)
    }
}