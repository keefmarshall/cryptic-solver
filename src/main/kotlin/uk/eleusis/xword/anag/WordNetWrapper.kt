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

    fun allSimilarWords(word: String): Collection<String> {
        val wordSet = dictionary.lookupAllIndexWords(word)

        return wordSet.indexWordArray
                .flatMap { indexWord ->
                    //indexWord.senses.map { PointerUtils.getDirectHyponyms(it) }
                    indexWord.senses.flatMap { PointerUtils.getHyponymTree(it).toList() }
                            .plus(indexWord.senses.flatMap { PointerUtils.getHypernymTree(it, 5).toList() })
                            .plus(indexWord.senses.flatMap { PointerUtils.getSynonymTree(it, 5).toList() })
                } // List<PointerTargetNodeList>
                .flatMap { it.toList() }  // List<PointerTarget>
                .flatMap { it.synset.words } // List<Word>
                .map { it.lemma.toLowerCase() } // List<String>
                .toSortedSet()
    }

}