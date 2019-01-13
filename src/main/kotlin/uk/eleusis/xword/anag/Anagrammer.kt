package uk.eleusis.xword.anag

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.SetValuedMap
import org.apache.commons.collections4.multimap.HashSetValuedHashMap
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import kotlin.coroutines.experimental.buildSequence

@Service
class Anagrammer(val wf: WordFile) {

    private val anagramMap = wf.getAnagramMap()

    fun findAnagrams(phrase: String): Set<String> {
        return anagramMap.get(sanitisePhrase(phrase).toSortedBag())
    }

    // Find words of the same size or smaller that can be made from the letters
    // in the supplied word
    fun findSub(word: String, minLength: Int = 0): Set<String> {
      val sanbag = sanitisePhrase(word).toBag()

      return anagramMap.keySet()
        .filter { sanbag.containsAll(it) }
        .flatMap { anagramMap.get(it) }
        .filter { it.length >= minLength }
        .toSortedSet()
    }

    // Find words of the same size or larger that contain the letters from
    // the supplied partial inside - e.g. if I passed in 'ring' I'd get 'string'
    // in the output (and lots of other stuff!)
    fun findPartial(partial: String): Set<String> {
        val sanbag = sanitisePhrase(partial).toBag()

        return anagramMap.keySet()
                .filter { it.containsAll(sanbag) }
                .flatMap { anagramMap.get(it) }
                .toSortedSet()
    }

    fun findPartialWithMaxLength(partial: String, maxLength: Int): Set<String> =
            findPartial(partial)
                    .filter { it.length <= maxLength}
                    .toSortedSet()

    fun findPartialWithExactLength(partial: String, length: Int): Set<String> =
            findPartial(partial)
                    .filter { it.length == length}
                    .toSortedSet()

    fun containsPartial(partial: String, actual: String): Boolean =
            sanitisePhrase(actual).toBag()
                    .containsAll(sanitisePhrase(partial).toBag())


    /**
     * This is a bit complicated to explain. For a clue which is e.g. (4,5) it will
     * find all combinations of 4 and 5 letter words, and check if any of the combinations
     * contains within it an anagram of the passed-in word. So I pass in 'peace', it will
     * find e.g. "peas close" as that contains a full anagram of 'peace' within it.
     *
     * This will generate a lot of false positives and phrases that don't make sense, the
     * idea is to then narrow it down by looking at individual word meanings.
     *
     * It returns a sequence, in case the response is very large. The sequence will be
     * sorted alphabetically, for easier comparison/merging.
     */
    fun findPhraseAnagramPartials(input: String, wordLengths: List<Int>): Sequence<List<String>> {

        val anagramMap = HashSetValuedHashMap<Bag<Char>, String>()
        val wordLists = wordLengths.map { length ->
            wf.getWordSet().filter { word -> word.length == length }
        }

        val sanitisedInput = sanitisePhrase(input).toSortedBag()

        // The possible combinations could get extremely large if we have 3-4 words of
        // middling lengths, let's not risk running out of memory but instead build
        // a sequence using Kotlin's new generators, which will be lazily populated
        // NB really important not to 'realise' the sequence by using e.g. toList()
        // as this may take up huge amounts of RAM unnecessarily.
        val combos = comboSequence(wordLists)
        return combos.map { Pair(it, it.joinToString()) }
                .map{ Pair(it.first, sanitisePhrase(it.second)) }
                .map{ Pair(it.first, it.second.toSortedBag())}
                .filter { it.second.containsAll(sanitisedInput) }
                .map { it.first }
    }

}
