package com.galuszkat.helloapp.web

import com.galuszkat.helloapp.core.Account
import com.galuszkat.helloapp.core.AccountService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val service: AccountService
) {

    @GetMapping("/list")
    fun list(): List<Account> {
        return service.list(200).get().collect(Collectors.toList())
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody request: AccountRequest): Account {
        return service.create(request.toDomain())
    }
}