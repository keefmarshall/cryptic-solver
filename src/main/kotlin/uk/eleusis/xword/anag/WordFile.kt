package uk.eleusis.xword.anag

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.SetValuedMap
import org.apache.commons.collections4.multimap.HashSetValuedHashMap
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import javax.annotation.PostConstruct

@Service
class WordFile(
  @Value("\${word.file}") private val filePath: String = "/UKACD18plus-keith.txt",
  @Value("\${word.file.useClasspath:true}") private val useClasspath: Boolean = true
) {

  private val wordSet = mutableSetOf<String>()

  private val sanitisedWordMap: SetValuedMap<String, String> = HashSetValuedHashMap<String, String>()

  private var anagramMap: SetValuedMap<Bag<Char>, String> = HashSetValuedHashMap<Bag<Char>, String>()

  @PostConstruct
  fun loadFile() = loadFile(filePath)

  fun loadFile(path: String) {
    if (useClasspath) {
      loadFileFromClassPath(path)
    } else {
      loadFileFromFilesystem(path)
    }
  }

  fun loadFileFromFilesystem(path: String) {
    val reader = Files.newBufferedReader(Paths.get(path), Charset.forName("UTF-8"))
    loadFileFromReader(reader)
  }

  fun loadFileFromClassPath(resourcePath: String) {
    // NB this doesn't use NIO.. might not be a big deal, just noting it here.
    val reader = BufferedReader(InputStreamReader(javaClass.getResourceAsStream(resourcePath)))
    loadFileFromReader(reader)
  }

  private fun loadFileFromReader(bufferedReader: BufferedReader) {
    bufferedReader.use { reader ->
      reader.readLines().forEach { line ->
        wordSet.add(line)

        val san = sanitisePhrase(line)
        sanitisedWordMap.put(san, line)

        val bag = san.toSortedBag()
        anagramMap.put(bag, line)
      }
    }
  }

  fun getAnagramMap(): SetValuedMap<Bag<Char>, String> = anagramMap

  fun getWordSet(): Set<String> = wordSet

  fun getSanitisedWordMap(): SetValuedMap<String, String> = sanitisedWordMap
}

