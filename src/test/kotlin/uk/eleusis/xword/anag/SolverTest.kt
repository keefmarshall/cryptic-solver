package uk.eleusis.xword.anag

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
            solver = Solver(wf, anagrammer)
        }
    }

    @Test
    fun tryFullAnagram() {
        val clue = ClueParser.parseClue("... and Pan's Dream, perhaps (9)")
        val possibles = solver.tryFullAnagram(clue)
        println(possibles)
    }
}