package uk.eleusis.xword.anag

import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

class AnagrammerTest {


    companion object {

        private val TESTFILE = "/UKACD18plus-keith.txt"

        lateinit var anagrammer: Anagrammer

        @JvmStatic
        @BeforeClass
        fun setUp() {
            val wf = WordFile(TESTFILE)
            wf.loadFile()

            anagrammer = Anagrammer(wf)
        }
    }

    @Test
    fun findSub() {
      time( "find sub") {
        val allMatches = anagrammer.findSub("bizarre", 4)
        assertEquals(35, allMatches.size)
        assertTrue(allMatches.contains("zebra"))
        allMatches.forEach(::println)
      }

      anagrammer.findSub("snebmaa", 4).forEach(::println)
    }

    @Test
    fun findPartial() {

        time("Find partials") {
            val allMatches = anagrammer.findPartial("Pan's")
            assertEquals(10017, allMatches.size)
            assertTrue(allMatches.contains("Caspian"))
        }

        time("Find partials of max length") {
            val matches8 = anagrammer.findPartialWithMaxLength("Pan's", 8)
            assertEquals(917, matches8.size)
            assertTrue(matches8.contains("Caspian"))
        }

        time("Find partials of exact length") {
            val matches8 = anagrammer.findPartialWithExactLength("Pan's", 8)
            assertEquals(545, matches8.size)
            assertTrue(matches8.contains("aspiring"))
        }

    }


    @Test
    fun findAnagrams() {
        time("Anagram lookup") {
            val anags = anagrammer.findAnagrams("Pan's Dream")
            assertEquals(1, anags.size)
            assertEquals("ampersand", anags.first())
        }

        println(anagrammer.findAnagrams("ten years"))
    }

  @Test
  fun findAnagrams2() {
    time("Anagram lookup 2") {
      val anags = anagrammer.findAnagrams("Cheam, its deed")
      assertEquals(1, anags.size)
      assertEquals("semi-detached", anags.first())
    }

    println(anagrammer.findAnagrams("Cheam, its deed"))
  }


  @Test
    fun containsPartial() {
        time("contains partial") {
            val con = anagrammer.containsPartial("Pan's", "ampersand")
            assertTrue(con)
        }
    }

    @Test
    fun findPhraseAnagramPartials() {
        time("phrase anagram partials") {
            val result = anagrammer.findPhraseAnagramPartials("peace", listOf(3, 4))
            assertEquals(listOf("ABC", "beep"), result.first())
        }
    }


}
