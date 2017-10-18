package uk.eleusis.xword.anag.web

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.eleusis.xword.anag.ClueParser
import uk.eleusis.xword.anag.Solver

@RestController
@RequestMapping("/api")
class SolutionController(@Autowired val solver: Solver) {

    private val logger = LogManager.getLogger(SolutionController::class.java.name)

    @RequestMapping("/solve")
    fun solve(@RequestParam("clue") clue: String,
              @RequestParam("known") knownString: String): Solver.Solution {

        logger.info("Got clue: $clue")
        logger.info("Got known: $knownString")
        val parsedClue = ClueParser.parseClue(clue)

        knownString.toCharArray()
                .mapIndexed { index, c ->
                    if (c != '?') {
                        logger.info("Got $index as $c")
                        parsedClue.knownLetters[index] = c
                    }
                }

        return solver.solve(parsedClue)
    }

    @RequestMapping("/parse")
    fun parseClue(@RequestParam("clue") clue: String) = ClueParser.parseClue(clue)
}
