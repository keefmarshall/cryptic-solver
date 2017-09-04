package uk.eleusis.xword.anag

data class Clue(val phrase: String,
                val phraseWords: List<String>,
                val totalLength: Int,
                val wordLengths: List<Int>,
                val separators: List<String>) {

    val knownLetters: List<String> = mutableListOf<String>()
}

