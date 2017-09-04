package uk.eleusis.xword.anag


import org.apache.commons.collections4.bag.TreeBag
import org.junit.Assert.*
import org.junit.Test

class TextUtilsTest {

    @Test
    fun sanitisePhrase() {
        assertEquals("thequickbrownfox", sanitisePhrase("The  Quick-BROwn Føx  "))
    }

    @Test
    fun toSortedBag() {
        val testBag = TreeBag(listOf('a', 'e', 'l', 'p', 'p', 's'))
        assertEquals(testBag, "apples".toSortedBag())
        // Bag.containsAll violates the Collection contract and returns true if the
        // members of the supplied collection all exist in the bag, respecting cardinality
        assertTrue("apples".toSortedBag().containsAll(listOf('a', 'p', 'l')))
        assertTrue("apples".toSortedBag().containsAll(listOf('a', 'p', 'l', 'p')))
        assertFalse("apples".toSortedBag().containsAll(listOf('a', 'p', 'l', 'p', 'p')))
    }

    @Test
    fun subsetOfExactLength() {
        val strings = listOf("apples", "oranges", "pears", "pineapples", "grapes", "kiwis", "watermelons")

        val result = subsetOfExactLength(strings, 11)
        result.forEach(::println)

        println()

        val result2 = subsetOfExactLength(strings, 21)
        result2.forEach(::println)

    }
}