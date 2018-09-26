package uk.eleusis.xword.anag

import org.junit.Assert.*
import org.junit.Test

class ClueParserTest {

  @Test
  fun parseClue() {
    val clue1 = ClueParser.parseClue("Public seems upset about a magic formula (4,6)")
    println(clue1)
    assertEquals("Public seems upset about a magic formula", clue1.phrase)
    assertEquals(listOf("Public", "seems", "upset", "about", "a", "magic", "formula"), clue1.phraseWords)

    println(ClueParser.parseClue("... and Pan's Dream, perhaps (9)"))

    val clue = ClueParser.parseClue("Promise to welcome terribly wary dramatist (10)")
    clue.knownLetters[5] = 'r'
    clue.knownLetters[9] = 't'
    println(clue)
  }

  @Test
  fun parseLengthString() {
    val (totalLength, lengths, separators) = ClueParser.parseLengthString("6,6")
    println("Got: $totalLength, $lengths, $separators")
    assertEquals(listOf(6, 6), lengths)
    assertEquals(12, totalLength)
    assertEquals(listOf(",", ""), separators)
  }

  @Test
  fun parseLengthString2() {
    val (totalLength, lengths, separators) = ClueParser.parseLengthString("6,6-4")
    println("Got: $totalLength, $lengths, $separators")
    assertEquals(listOf(6, 6, 4), lengths)
    assertEquals(16, totalLength)
    assertEquals(listOf(",", "-", ""), separators)
  }

  @Test
  fun parseLengthString3() {
    val (totalLength, lengths, separators) = ClueParser.parseLengthString("12")
    println("Got: $totalLength, $lengths, $separators")
    assertEquals(listOf(12), lengths)
    assertEquals(12, totalLength)
    assertEquals(listOf(""), separators)
  }

}
