package uk.eleusis.xword.anag.web

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.eleusis.xword.anag.Anagrammer
import uk.eleusis.xword.anag.Clue
import uk.eleusis.xword.anag.ClueParser
import uk.eleusis.xword.anag.Solver

@RestController
@RequestMapping("/api")
class SolutionController(
  val solver: Solver,
  val anag: Anagrammer
) {

    private val logger = LogManager.getLogger(SolutionController::class.java.name)

    @RequestMapping("/solve")
    fun solve(@RequestParam("clue") clue: String,
              @RequestParam("known") knownString: String): Solver.Solution {

        logger.info("Got clue: $clue")
        logger.info("Got known: $knownString")
        val parsedClue = ClueParser.parseClue(clue)

        knownStringToLetters(knownString).mapIndexed { index, c -> parsedClue.knownLetters[index] = c }

        return solver.solve(parsedClue)
    }

    @RequestMapping("/parse")
    fun parseClue(@RequestParam("clue") clue: String) = ClueParser.parseClue(clue)


    @RequestMapping("/anagramsub")
    fun anagramsub(@RequestParam(value = "phrase") phrase: String,
                   @RequestParam(value = "minLength", required = false) minLength: Int?): Set<String> {
      return anag.findSub(phrase, minLength ?: 0)
    }

    @RequestMapping("/anagrams")
    fun anagrams(@RequestParam("phrase") phrase: String) = anag.findAnagrams(phrase)

    @RequestMapping("/knownLetterMatches")
    fun knownLetterMatches(@RequestParam("known") knownString: String): Collection<String> {
      val knownLetters = knownStringToLetters(knownString)
      return solver.tryKnownLetterMatch(knownLetters, knownLetters.size)
    }

    @RequestMapping("/parseLengthString")
    fun parseLengthString(@RequestParam("lengthString") lengthString: String): Clue {
      val (totalLength, lengths, separators) = ClueParser.parseLengthString(lengthString)
      return Clue("", emptyList(), totalLength, lengths, separators)
    }
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private fun knownStringToLetters(knownString: String): Array<Char?> {
    val knownLetters = Array<Char?>(knownString.length) { _ -> null }
    knownString.toCharArray()
        .mapIndexed { index, c ->
          if (c != '?' && c != ' ') {
            knownLetters[index] = c
          }
        }
    return knownLetters
  }

}
