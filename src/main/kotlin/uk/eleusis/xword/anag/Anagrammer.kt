package uk.eleusis.xword.anag

class Anagrammer(val wf: WordFile) {

    private val anagramMap = wf.getAnagramMap()

    fun findAnagrams(phrase: String): Set<String> {
        return anagramMap.get(sanitisePhrase(phrase).toSortedBag())
    }

    // Find words of the same size or larger that contain the letters from
    // the supplied partial inside - e.g. if I passed in 'ring' I'd get 'string'
    // in the output (and lots of other stuff!)
    fun findPartial(partial: String): Set<String> {
        val sanbag = sanitisePhrase(partial).toBag()
        val output = mutableSetOf<String>()
        anagramMap.keySet().forEach { key ->
            if (key.containsAll(sanbag)) {
                output.addAll(anagramMap.get(key))
            }
        }

        return output.toSortedSet()
    }

    fun findPartialWithMaxLength(partial: String, maxLength: Int): Set<String> =
            findPartial(partial)
                    .filter { it.length <= maxLength}
                    .toSortedSet()

    fun findPartialWithExactLength(partial: String, length: Int): Set<String> =
            findPartial(partial)
                    .filter { it.length == length}
                    .toSortedSet()

    fun containsPartial(partial: String, actual: String): Boolean =
            sanitisePhrase(actual).toBag()
                    .containsAll(sanitisePhrase(partial).toBag())

}