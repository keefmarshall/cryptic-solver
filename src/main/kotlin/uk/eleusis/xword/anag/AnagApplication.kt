package uk.eleusis.xword.anag

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class AnagApplication {

  @Autowired lateinit var wf: WordFile
  @Value("\${word.file.extra}") var extraWords: String? = null

  @PostConstruct
  fun init() {
    if (extraWords != null) {
      wf.loadFile(extraWords ?: "") // Kotlin bug? Can't infer that it's tested as not null..
    }
  }
}

fun main(args: Array<String>) {
    SpringApplication.run(AnagApplication::class.java, *args)
}
