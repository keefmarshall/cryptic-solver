package uk.eleusis.xword.anag

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AnagApplication

fun main(args: Array<String>) {
    SpringApplication.run(AnagApplication::class.java, *args)
}
