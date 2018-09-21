package uk.eleusis.xword.anag

import org.springframework.stereotype.Service

/**
 * Looks for word inclusions in the clue
 */
@Service
class Inclusioner(val wf: WordFile) {

  fun findInclusions(clue: Clue): List<String> {
    val allWords = wf.getSanitisedWordMap()
    val clueString = clue.phraseWords.joinToString("", transform = ::sanitisePhrase)

    val inclusions = mutableListOf<String>()
    if (clueString.length >= clue.totalLength) {
      (0..clueString.length - clue.totalLength).forEach {
        val potential = clueString.substring(it, it + clue.totalLength)
        if (allWords.containsKey(potential)) {
          inclusions.addAll(allWords[potential])
        }
      }
    }

    return inclusions
  }
}
