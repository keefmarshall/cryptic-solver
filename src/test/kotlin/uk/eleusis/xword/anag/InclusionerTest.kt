package uk.eleusis.xword.anag

import org.junit.Assert.*
import org.junit.Test

class InclusionerTest {

  private val TESTFILE = "/UKACD18plus-keith.txt"

  @Test
  fun findInclusions() {
    val wf = WordFile(TESTFILE)
    wf.loadFile()

    assertTrue(wf.getWordSet().contains("Exe"))
    assertTrue(wf.getSanitisedWordMap().containsKey("exe"))

    val incl = Inclusioner(wf)

    val inclusions = incl.findInclusions(ClueParser.parseClue("River seen in Sussex, Essex and in Devon! (3)"))
    println(inclusions)
    assertTrue(inclusions.contains("Exe"))
  }
}
