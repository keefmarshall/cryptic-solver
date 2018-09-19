package uk.eleusis.xword.anag

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.SortedBag
import org.apache.commons.collections4.bag.HashBag
import org.apache.commons.collections4.bag.TreeBag
import org.apache.commons.lang3.NotImplementedException
import org.apache.commons.lang3.StringUtils
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class Solver(val wf: WordFile, val anag: Anagrammer, val wn: WordNetWrapper) {

    data class Solution(val words: List<String>, val clue: Clue)

    private val logger = LogManager.getLogger(Solver::class.java.name)

    fun solve(input: String): Solution = solve(ClueParser.parseClue(input))

    fun solve(clue: Clue): Solution {

        // so, for now let's collect a Bag of answers, and return them in
        // descending order of popularity
        val resultBag: Bag<String> = HashBag<String>()

        resultBag.addAll(tryFullAnagram(clue))
        resultBag.addAll(trySimplisticWNMatch(clue, false))
//        if (clue.hasKnownLetters()) {
//            resultBag.addAll(wf.getWordSet().filter(knownLettersFilter(clue.knownLetters)))
//        }
        // TODO: other solvey things


        val knownLetterResults = tryKnownLetterMatch(clue)
        resultBag.addAll(tryPartialAnagram(clue, knownLetterResults))

        val knownFilter = knownLettersFilter(clue.knownLetters)
        var results = resultBag
                .uniqueSet()
                .filter(knownFilter)
                .toList()
                .sortedByDescending { s -> resultBag.getCount(s) }

        if (results.isEmpty()) { // || results.size < 3) {
            logger.info("results empty/small, returning all words that match known letters ${clue.knownLetters}")
            results = results.plus(knownLetterResults)
        }

        return Solution(results, clue)
    }

    fun tryKnownLetterMatch(clue: Clue): List<String> {
      val knownFilter = knownLettersFilter(clue.knownLetters)
      val sanitisedWordMap = wf.getSanitisedWordMap()
      return sanitisedWordMap
        .keySet()
        .filter { it.length == clue.totalLength }
        .filter(knownFilter)
        .map { sanitisedWordMap[it].first() }
    }

    // This works if we have the exact single word or phrase in the dictionary,
    // but not if the solution is a combination of words we don't have as a phrase.
    fun tryFullAnagram(clue: Clue): List<String> {
        // find a set of whole words that match the total length
        val combos = subsetOfExactLength(clue.phraseWords, clue.totalLength)
        if (combos.isEmpty()) // there's no obvious anagram
            return emptyList()

        // For each combination, find anagrams
        return combos.map { it.joinToString(separator = "") }
                .map(::sanitisePhrase)
                .flatMap(anag::findAnagrams)
                .toList()
    }

    fun tryPartialAnagram(clue: Clue, words: List<String>? = null): List<String> {
        val workingSet = words ?: tryKnownLetterMatch(clue)

        return workingSet.filter { matchPartialAnagrams(clue, it) }
    }

    fun matchPartialAnagrams(clue: Clue, word: String): Boolean {
      return clue.phraseWords
        .filter { it.length > 3 } // otherwise we'll get too many
        .any { anag.containsPartial(it, word) }
    }

    fun trySimplisticWNMatch(clue: Clue, tryCombos: Boolean): List<String> {
        // take each word in the clue, find all similar words. Combine all the lists
        // into one big list. Find all the combos that match the total clue length.
        // Very simplistic, lots of issues with this approach, just seeing if it
        // ever works.
        val biglist = clue.phraseWords.flatMap(wn::allSimilarWords).map(::sanitisePhrase)
        println("Got total simplistic match words: ${biglist.size}")

        val exacts = biglist
          .filter { it.length == clue.totalLength }
          .map {
            val readable = wf.getSanitisedWordMap()[it]
            if (readable == null || readable.isEmpty()) it else readable.first()
          }

        println("Got total exact length simplistic match words: ${exacts.size}")

        return if (tryCombos) {
            val combos: List<List<String>> = subsetOfExactLength(biglist, clue.totalLength)
            // we add the exact matches twice, to boost their score
            combos.map { it.joinToString(separator = "") } + exacts
        }
        else { exacts }
    }
}

fun matchKnownLetters(word: String, known: Array<Char?>): Boolean {
    var match = true
    if (word.isNotEmpty() && known.isNotEmpty()) {
        val sword = sanitisePhrase(word)
        (0 until known.size).forEach {
            if (known[it] != null) {
                if (known[it] != sword[it]) {
                    match = false
                    return@forEach
                }
            }
        }
    }
    else { match = false }

    return match
}

fun knownLettersFilter(known: Array<Char?>): (String) -> Boolean =
        { word: String -> matchKnownLetters(word, known) }

