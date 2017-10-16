package uk.eleusis.xword.anag

import org.junit.Test

class ClueParserTest {

    @Test
    fun parseClue() {
        println(ClueParser.parseClue("Public seems upset about a magic formula (4,6)"))
        println()
        println(ClueParser.parseClue("... and Pan's Dream, perhaps (9)"))
        println()

        val clue = ClueParser.parseClue("Promise to welcome terribly wary dramatist (10)")
        clue.knownLetters[5] = 'r'
        clue.knownLetters[9] = 't'
        println(clue)
    }

}