package com.galuszkat.helloapp

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class HelloController {

    @GetMapping("/")
    fun hello(): String {
        val time = LocalDateTime.now().toString()
        println("Hello request / %s".format(time))

        Thread.sleep(220)
        
        return time
    }
}