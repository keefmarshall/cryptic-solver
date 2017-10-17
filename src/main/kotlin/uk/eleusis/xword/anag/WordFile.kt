package uk.eleusis.xword.anag

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.SetValuedMap
import org.apache.commons.collections4.multimap.HashSetValuedHashMap
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import javax.annotation.PostConstruct

@Service
class WordFile(@Value("\${word.file}") private val filePath: String) {

    private val wordSet = mutableSetOf<String>()

    private val sanitisedWordMap: SetValuedMap<String, String>
            = HashSetValuedHashMap<String, String>()

    private var anagramMap: SetValuedMap<Bag<Char>, String>
            = HashSetValuedHashMap<Bag<Char>, String>()

    @PostConstruct
    fun loadFile() = loadFile(filePath)

    fun loadFile(path: String) {
        val stream = Files.newBufferedReader(Paths.get(path), Charset.forName("UTF-8"))
        stream.use { reader ->
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

