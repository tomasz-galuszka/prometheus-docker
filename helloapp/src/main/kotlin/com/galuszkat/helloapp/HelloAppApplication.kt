package com.galuszkat.helloapp

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloAppApplication

fun main(args: Array<String>) {
	runApplication<HelloAppApplication>(*args) {
		setBannerMode(Banner.Mode.OFF)
	}
}
