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
        val strings = sequenceOf("apples", "oranges", "pears", "pineapples", "grapes", "kiwis", "watermelons")

        val result = subsetOfExactLength(strings, 11)
        result.forEach(::println)

        println()

        val result2 = subsetOfExactLength(strings, 21)
        result2.forEach(::println)

    }

    @Test
    fun comboSequence2() {
        val wordLists = listOf(listOf("two", "three"), listOf("apples", "pears"))
        val combos = comboSequence(wordLists).toHashSet()

        println(combos)

        assertEquals(4, combos.size)
        assertTrue(combos.contains(listOf("two", "apples")))
        assertTrue(combos.contains(listOf("three", "apples")))
        assertTrue(combos.contains(listOf("two", "pears")))
        assertTrue(combos.contains(listOf("three", "pears")))
    }

    @Test
    fun comboSequence3() {
        val wordLists = listOf(listOf("two", "three"), listOf("apples", "pears"), listOf("fresh", "frozen"))
        val combos = comboSequence(wordLists).toHashSet()

        println(combos)

        assertEquals(8, combos.size)
        assertTrue(combos.contains(listOf("two", "apples", "fresh")))
        assertTrue(combos.contains(listOf("three", "apples", "fresh")))
        assertTrue(combos.contains(listOf("two", "pears", "fresh")))
        assertTrue(combos.contains(listOf("three", "pears", "fresh")))
        assertTrue(combos.contains(listOf("two" ,"apples", "frozen")))
        assertTrue(combos.contains(listOf("three" ,"apples", "frozen")))
        assertTrue(combos.contains(listOf("two", "pears", "frozen")))
        assertTrue(combos.contains(listOf("three", "pears", "frozen")))
    }
}
