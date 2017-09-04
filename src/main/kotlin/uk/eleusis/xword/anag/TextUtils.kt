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
                        partial: List<String> = mutableListOf(),
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
