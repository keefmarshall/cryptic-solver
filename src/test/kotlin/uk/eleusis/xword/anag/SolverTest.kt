package uk.eleusis.xword.anag

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.bag.HashBag
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

class SolverTest() {

    companion object {

        private val TESTFILE = "/Users/keith/Downloads/UKACD17/sorted/UKACD18plus-keith.txt"

        lateinit var solver: Solver

        @JvmStatic
        @BeforeClass
        fun setUp() {
            val wf = WordFile(TESTFILE)
            wf.loadFile()

            val anagrammer = Anagrammer(wf)
            val wn = WordNetWrapper()
            wn.init()

            solver = Solver(wf, anagrammer, wn)
        }
    }

    @Test
    fun solve() {
        val result = solver.solve("... and Pan's Dream, perhaps (9)")
        //assertEquals(1, result.words.size)
        //assertEquals("ampersand", result.words.first())
        assertTrue(result.words.contains("ampersand"))
    }

    @Test
    fun solveAnother() {
        val clue = ClueParser.parseClue("small fellow eaten by diabolical sea creature (9)")
        clue.knownLetters[4] = 'l'
        clue.knownLetters[6] = 'i'
        clue.knownLetters[8] = 'h'

        val result = solver.solve(clue)

        println(result.words.size)
        println(result.words)

        //assertEquals(1, result.words.size)
        //assertEquals("ampersand", result.words.first())
    }

    @Test
    fun solveAnother2() {
        val clue = ClueParser.parseClue("Mixed collection up prior to reshuffle (9)")
        clue.knownLetters[2] = 't'
        clue.knownLetters[6] = 'r'

        val result = solver.solve(clue)

        println(result.words.size)
        println(result.words)
    }

    @Test
    fun solveAnother3() {
        // NB NEVER COMPLETES without letters
        val clue = ClueParser.parseClue("Unusual portion of toasted cheese (7)")
        clue.knownLetters[2] = 'r'
        clue.knownLetters[6] = 't'

        val result = solver.solve(clue)

        println(result.words.size)
        println(result.words)
    }

    @Test
    fun solveAnother4() {
        val clue = ClueParser.parseClue("It may appear smart on the stern of a boat (7)")
        clue.knownLetters[0] = 't'
        clue.knownLetters[2] = 'a'
        clue.knownLetters[6] = 'm'

        val result = solver.solve(clue)

        println(result.words.size)
        println(result.words)
    }

    @Test
    fun solveAnother5() {
        val clue = ClueParser.parseClue("Cruel animal brush, it should be thrown out (7)")
        clue.knownLetters[0] = 'b'
        clue.knownLetters[6] = 'h'

        val result = solver.solve(clue)

        println(result.words.size)
        println(result.words)
    }

    //Sort of house in Cheam, its deed is mislaid (4-8)
    // semi-detached
    // - seems to be some issue with the hyphenated part
    @Test
    fun solveAnother6() {
      val clue = ClueParser.parseClue("Sort of house in Cheam, its deed is mislaid (4-8)")
      clue.knownLetters[0] = 's'
      clue.knownLetters[4] = 'd'

      val result = solver.solve(clue)

      println(result.words.size)
      println(result.words)
      assertTrue(result.words.contains("semi-detached"))
    }

    // Acclamation of unusual papal custom (8) - Applause
    @Test
    fun solveAnother7() {
      val clue = ClueParser.parseClue("Acclamation of unusual papal custom (8)")
      clue.knownLetters[3] = 'l'

      val result = time("Solve applause") {
        solver.solve(clue)
      }

      assertTrue(result.words.contains("applause"))
    }


    @Test
    fun tryFullAnagram() {
        val clue = ClueParser.parseClue("... and Pan's Dream, perhaps (9)")
        val possibles = solver.tryFullAnagram(clue)
        println(possibles)
    }

    @Test
    fun tryFullAnagram2() {
      val clue = ClueParser.parseClue("Sort of house in Cheam, its deed is mislaid (4-8)")
      val possibles = solver.tryFullAnagram(clue)
      println(possibles)
      assertTrue(possibles.contains("semi-detached"))
    }

    @Test
    fun tryPartialAnagram() {
      val clue = ClueParser.parseClue("Acclamation of unusual papal custom (8)")
      clue.knownLetters[3] = 'l'

      val possibles = solver.tryPartialAnagram(clue)
      println(possibles)
      assertTrue(possibles.contains("applause"))
    }

    @Test
    fun trySimplisticWNMatch() {
        val clue = ClueParser.parseClue("Drink to self-publicity? (4)")
        val possibles = solver.trySimplisticWNMatch(clue, true)
        possibles.forEach {
            println("$it: ${it.javaClass.name}")
        }
    }

    @Test
    fun trySimplisticWNMatch2() {
      val clue = ClueParser.parseClue("Sort of house in Cheam, its deed is mislaid (4-8)")
      val possibles = solver.trySimplisticWNMatch(clue, false)
      possibles.forEach {
        println("$it: ${it.javaClass.name}")
      }
    }

    @Test
    fun testForSemiDetachedBug() {
      val clue = ClueParser.parseClue("Sort of house in Cheam, its deed is mislaid (4-8)")
      val apossibles = solver.tryFullAnagram(clue)
      val spossibles = solver.trySimplisticWNMatch(clue, false)

      val resultBag: Bag<String> = HashBag<String>()
      resultBag.addAll(apossibles)
      assertTrue(resultBag.contains("semi-detached"))

      resultBag.addAll(spossibles)
      assertTrue(resultBag.contains("semi-detached"))

      val knownFilter = knownLettersFilter(clue.knownLetters)

      var results = resultBag
        .uniqueSet()
        .filter(knownFilter)
        .toList()
        .sortedByDescending { s -> resultBag.getCount(s) }

      assertTrue(results.contains("semi-detached"))
    }

    @Test
    fun matchKnownLetters() {
        val known = Array<Char?>(4, { _ -> null})
        known[1] = 'e'
        known[3] = 'd'

        assertTrue(matchKnownLetters("mead", known))
        assertFalse(matchKnownLetters("bcsg", known))
    }

    @Test
    fun matchKnownLetters2() {
        val known = Array<Char?>(7, { _ -> null })
        known[0] = 'h'
        known[1] = null
        known[2] = null
        known[3] = null
        known[4] = 't'
        known[5] = 'u'
        known[6] = 'e'

        assertTrue(matchKnownLetters("habitue", known))
    }

    @Test
    fun matchKnownLetters3() {
        val known = Array<Char?>(12, { _ -> null})
        known[0] = 's'
        known[5] = 'e'

        assertTrue(matchKnownLetters("semi-detached", known))
        assertFalse(matchKnownLetters("secretballot", known))
    }

    @Test
    fun knownLettersFilter() {
      val known = Array<Char?>(12, { _ -> null})
      known[0] = 's'
      known[5] = 'e'

      val filter = knownLettersFilter(known)
      assertTrue(filter("semi-detached"))
      assertFalse(filter("secretballot"))
    }
}
