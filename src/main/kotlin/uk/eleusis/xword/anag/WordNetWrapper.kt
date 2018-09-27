package uk.eleusis.xword.anag

import net.sf.extjwnl.data.PointerUtils
import net.sf.extjwnl.dictionary.Dictionary
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class WordNetWrapper {

    private lateinit var dictionary: Dictionary

    @PostConstruct
    fun init() {
        dictionary = Dictionary.getDefaultResourceInstance()
    }

    fun allSimilarWords(word: String): Sequence<String> {
        val wordSet = dictionary.lookupAllIndexWords(word)

        return wordSet.indexWordArray.asSequence()
          .flatMap { indexWord ->
            //indexWord.senses.map { PointerUtils.getDirectHyponyms(it) }
            indexWord.senses.flatMap { PointerUtils.getHyponymTree(it).toList() }
              .plus(indexWord.senses.flatMap { PointerUtils.getHypernymTree(it, 5).toList() })
              .plus(indexWord.senses.flatMap { PointerUtils.getSynonymTree(it, 5).toList() })
              .asSequence()
          } // List<PointerTargetNodeList>
          .flatMap { it.asSequence() }  // List<PointerTarget>
          .flatMap { it.synset.words.asSequence() } // List<Word>
          .map { it.lemma.toLowerCase() } // List<String>
          .distinct() //.toSortedSet()
          .sorted() // internally this converts toList() so do it last
    }

}
