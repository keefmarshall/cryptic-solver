package uk.eleusis.xword.anag

object ClueParser {

    /**
     *  "... and Pan's Dream, perhaps (9)"
     *  "Public seems upset about a magic formula (4,6)"
     */
    fun parseClue(input: String): Clue {

        val lengths = mutableListOf<Int>()
        val separators = mutableListOf<String>()

        val regex1 = Regex("(.*)\\((.+)\\)\\s*$")
        val lenMatch = regex1.find(input)
        if (lenMatch != null) {
            // NB [0] is always the full string, we need [1], [2]!
            val clueStr = lenMatch.groupValues[1].trim()
            val phraseWords = clueStr
                    .split(Regex("[^\\w]+"))
                    .filter { it.isNotEmpty() }
                    .toList()

            val lenStr = lenMatch.groupValues[2]

            // could be e.g. '8', '4,6', '2-2,3' etc, might have spaces
            val regex2 = Regex("(\\d+)([^\\d]*)")
            val segMatches = regex2.findAll(lenStr)

            segMatches.forEach { match ->
                lengths.add(match.groupValues[1].toInt())
                if (match.groupValues.size > 2) {
                    separators.add(match.groupValues[2])
                }
            }

//            println("lengths: $lengths")
//            println("separators: $separators")

            val totalLength = lengths.reduce { sum, element -> sum + element }

            return Clue(clueStr, phraseWords, totalLength, lengths, separators)
        }

        throw IllegalArgumentException("Input $input is not a valid crossword clue")
    }

}