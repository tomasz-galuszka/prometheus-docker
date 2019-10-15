package com.galuszkat.helloapp

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
        private val repository: WordRepository
) {

    @GetMapping("/")
    fun hello(): List<Word> {
        return repository.findAll()
    }
}