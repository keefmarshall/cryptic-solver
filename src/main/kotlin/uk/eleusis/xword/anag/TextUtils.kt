package uk.eleusis.xword.anag

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.SortedBag
import org.apache.commons.collections4.bag.HashBag
import org.apache.commons.collections4.bag.TreeBag
import org.apache.commons.lang3.StringUtils
import kotlin.coroutines.experimental.buildSequence

// private const val allhighchars        = "àáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ"
private const val usedHighChars = "àáâãäåçèéêëìíîïñòóôõöøùúûü"
private const val usedReplaceChars = "aaaaaaceeeeiiiinoooooouuuu"

fun sanitisePhrase(phrase: String): String {
    return StringUtils
            .replaceChars(phrase.toLowerCase(), usedHighChars, usedReplaceChars)
            .filter(Char::isLetter)
}

fun String.toBag(): Bag<Char> = HashBag(this.toList())
fun String.toSortedBag(): SortedBag<Char> = TreeBag(this.toList())

/**
 *
 * In Python we can do this:
 *
    def subset_sum(numbers, target, partial=[], partial_sum=0):
        if partial_sum == target:
            yield partial
        if partial_sum >= target:
            return
        for i, n in enumerate(numbers):
            remaining = numbers[i + 1:]
            yield from subset_sum(remaining, target, partial + [n], partial_sum + n)

    - we can use Kotlin's new coroutines to do something similar - this is the
    'generator pattern'
 */

fun subsetOfExactLength(strings: List<String>,
                        target: Int,
                        partial: List<String> = emptyList(),
                        partialSum: Int = 0): List<List<String>> = buildSequence {

        if (partialSum == target)
            yield(partial)
        if (partialSum >= target)
            return@buildSequence

        strings.forEachIndexed { i, cur ->
            val remaining = strings.drop(i + 1)
            val result = subsetOfExactLength(remaining, target, partial + cur, partialSum + cur.length)
            yieldAll(result)
        }

    }.toList()


fun subsetOfMaxLength(strings: List<String>,
                        target: Int,
                        partial: List<String> = emptyList(),
                        partialSum: Int = 0): List<List<String>> = buildSequence {

    if (partialSum in 1..target)
        yield(partial)
    if (partialSum >= target)
        return@buildSequence

    strings.forEachIndexed { i, cur ->
        val remaining = strings.drop(i + 1)
        val result = subsetOfMaxLength(remaining, target, partial + cur, partialSum + cur.length)
        yieldAll(result)
    }

}.toList()

/**
 * Generate a sequence of strings with all possible combinations from the supplied lists
 * e.g. if supplied [[two, three],[apples, pears]] would produce:
 * [twoapples, twopears, threeapples, threepears]
 */
fun comboSequence(wordLists: List<List<String>>, current: List<String> = emptyList()): Sequence<List<String>> =
        buildSequence {
            if (wordLists.isEmpty()) {
                yield(current)
            }
            else {
                val nextList = wordLists.first()
                val remainder = wordLists.drop(1)
                nextList.forEach {
                    val result = comboSequence(remainder, current + it)
                    yieldAll(result)
                }
            }
        }
