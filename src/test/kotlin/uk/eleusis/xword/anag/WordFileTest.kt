package uk.eleusis.xword.anag

import org.apache.commons.collections4.bag.TreeBag
import org.junit.Test

import org.junit.Assert.*

class WordFileTest {

  private val TESTFILE = "/Users/keith/Downloads/UKACD17/sorted/UKACD18plus-keith.txt"
  private val TESTFILE2 = "/Users/keith/Downloads/UKACD17/keith-extra-words.txt"

  private val TESTFILE_RESOURCE = "/UKACD18plus-keith.txt"
  private val TESTFILE2_RESOURCE = "/keith-extra-words.txt"

  @Test
  fun loadFileFromFilesystem() {
      val wf = WordFile(TESTFILE, false)

      time("File load") {
          wf.loadFile()
      }

      time("Additional file load") {
          wf.loadFile(TESTFILE2)
      }

      assertTrue(wf.getWordSet().contains("habitué"))
      assertTrue(wf.getSanitisedWordMap().keySet().contains("habitue"))
  }

  @Test
  fun loadFileFromClasspath() {
    val wf = WordFile(TESTFILE_RESOURCE, true)

    time("File load") {
      wf.loadFile()
    }

    time("Additional file load") {
      wf.loadFile(TESTFILE2_RESOURCE)
    }

    assertTrue(wf.getWordSet().contains("habitué"))
    assertTrue(wf.getSanitisedWordMap().keySet().contains("habitue"))
  }
}

