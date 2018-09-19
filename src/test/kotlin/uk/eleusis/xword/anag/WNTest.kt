package uk.eleusis.xword.anag

import net.sf.extjwnl.dictionary.Dictionary
import org.junit.Test

import org.junit.Assert.*


class WNTest {

    @Test
    fun loadWN() {
        val dictionary = Dictionary.getDefaultResourceInstance()
        assertNotNull(dictionary)

        println(dictionary.lookupAllIndexWords("ampersand"))
    }

    @Test
    fun allSimilarWords() {
        val wn = WordNetWrapper()
        wn.init()

        val result = wn.allSimilarWords("mead")
        println(result.filter { it.length <= 6 })
        println(result.size)

        val result2 = wn.allSimilarWords("acclamation")
        println(result2)
        val result3 = wn.allSimilarWords("applause")
        println(result3)
    }

}
