package uk.eleusis.xword.anag

import org.apache.commons.collections4.bag.TreeBag
import org.junit.Test

import org.junit.Assert.*

class WordFileTest {

    private val TESTFILE = "/Users/keith/Downloads/UKACD17/sorted/UKACD18plus-keith.txt"
    private val TESTFILE2 = "/Users/keith/Downloads/UKACD17/keith-extra-words.txt"

    @Test
    fun loadFile() {
        val wf = WordFile(TESTFILE)

        time("File load") {
            wf.loadFile()
        }

        time("Additional file load") {
            wf.loadFile(TESTFILE2)
        }

        assertTrue(wf.getWordSet().contains("habitué"))
        assertTrue(wf.getSanitisedWordMap().keySet().contains("habitue"))
    }

}

