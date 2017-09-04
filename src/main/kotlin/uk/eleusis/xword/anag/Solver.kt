package uk.eleusis.xword.anag

import org.apache.commons.lang3.NotImplementedException
import org.apache.commons.lang3.StringUtils

class Solver(val wf: WordFile, val anag: Anagrammer) {

    data class Solution(val words: List<String>, val clue: Clue)

    fun solve(input: String): Solution = solve(ClueParser.parseClue(input))

    fun solve(clue: Clue): Solution {





        return Solution(listOf("unknown"), clue)
    }

    fun tryFullAnagram(clue: Clue): List<String> {
        // find a set of whole words that match the total length
        val combos = subsetOfExactLength(clue.phraseWords, clue.totalLength)
        if (combos.isEmpty()) // there's no obvious anagram
            return emptyList()

        // For each combination, find anagrams
        return combos.map { StringUtils.join(it) }
                .map(::sanitisePhrase)
                .flatMap(anag::findAnagrams)
                .toList()
    }

    fun tryPartialAnagram(clue: Clue): List<String> {
        // TODO: implement this!
        throw NotImplementedException("tryPartialAnagram not yet implemented")
    }
}