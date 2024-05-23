package udesc.eso.ddm.infra.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import udesc.eso.ddm.infra.http.TranslationHttpDAO
import udesc.eso.ddm.model.TranslationRequest
import udesc.eso.ddm.model.TranslationResponse
import udesc.eso.ddm.model.Word
import java.util.UUID

class WordRepository(private val db: FirebaseFirestore, private val dao: TranslationHttpDAO) {

    suspend fun translateWord(word: TranslationRequest): Word {
        val translationResponse: TranslationResponse = dao.translateWord(word)
        val word = Word(
            uuid = UUID.randomUUID().toString(),
            word = translationResponse.word,
            fromLanguage = translationResponse.from,
            toLanguage = translationResponse.to,
            meaning = translationResponse.meaning,
            translation = translationResponse.translation,
            fromLanguageExampleSentence = translationResponse.fromLanguageExampleSentence,
            toLanguageExampleSentence = translationResponse.toLanguageExampleSentence
        )
        return word
    }

    suspend fun saveWordToDb(word: Word): String {
        val documentReference = db.collection("words").add(word).await()
        return documentReference.id
    }

    suspend fun getWord(word: String, fromLanguage: String, toLanguage: String): Word? {
        val querySnapshot = db.collection("words")
            .whereEqualTo("word", word)
            .whereEqualTo("fromLanguage", fromLanguage)
            .whereEqualTo("toLanguage", toLanguage)
            .get()
            .await()

        return if (querySnapshot.documents.isNotEmpty()) {
            querySnapshot.documents.first().toObject(Word::class.java)
                ?.copy(uuid = querySnapshot.documents.first().id)
        } else {
            null
        }
    }
    suspend fun getWordbyId(wordId: String): Word? {
        val wordRef = db.collection("words").document(wordId).get().await()
        return wordRef.toObject(Word::class.java)
    }

}