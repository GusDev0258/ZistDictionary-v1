package udesc.eso.ddm.infra.repository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import udesc.eso.ddm.model.Dictionary
import udesc.eso.ddm.model.Word

class DictionaryRepository(
    private val db: FirebaseFirestore,
    private val userRepository: UserRepository
) {
    private val userId =
        userRepository.getCurrentUserId() ?: throw IllegalStateException("User not logged in")

    suspend fun loadDictionaryWords(dictionaryUuid: String?): Flow<Word?>? {
        val dictionary = getUserDictionaryById(dictionaryUuid)
        val words = dictionary?.let {
            it.words.map { wordId ->
                this.getWordbyId(wordId)
            }
        }
        return words?.asFlow()
    }
    fun loadDictionaryWordsRealtime(dictionaryUuid: String, onWordsChanged: (List<Word?>) -> Unit) {
        db.collection("users")
            .document(userId)
            .collection("dictionaries")
            .whereEqualTo("uuid", dictionaryUuid)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Log.w("DictionaryRepository", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val dictionaryDocument = querySnapshot.documents.first()
                    val dictionary = dictionaryDocument.toObject(Dictionary::class.java)

                    dictionary?.let {
                        val wordIds = it.words
                        val words = mutableListOf<Word?>()

                        wordIds.forEach { wordId ->
                            db.collection("words")
                                .document(wordId)
                                .addSnapshotListener { wordSnapshot, wordException ->
                                    if (wordException != null) {
                                        Log.w("DictionaryRepository", "Listen failed.", wordException)
                                        return@addSnapshotListener
                                    }

                                    val word = wordSnapshot?.toObject(Word::class.java)
                                    words.add(word)

                                    if (words.size == wordIds.size) {
                                        onWordsChanged(words)
                                    }
                                }
                        }
                    }
                }
            }
    }

    suspend fun getUserDictionaryById(id: String?): Dictionary? {
        var dic: Dictionary? = null
        try {
            val querySnapshot = db.collection("users")
                .document(userId)
                .collection("dictionaries")
                .whereEqualTo("uuid", id)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val dictionaryDocument = querySnapshot.documents.first()
                dic = dictionaryDocument.toObject(Dictionary::class.java)
                Log.d("getUserDictionaryById", "Dicionário recuperado com sucesso: $dic")
            } else {
                Log.d("getUserDictionaryById", "Nenhum dicionário encontrado com o UUID: $id")
            }
        } catch (e: Exception) {
            Log.e(
                "getUserDictionaryById",
                "Erro ao recuperar o dicionário com UUID $id: ${e.message}",
                e
            )
        }
        return dic
    }

    suspend fun getUserDictionaries(): Flow<List<Dictionary>> = callbackFlow {
        val listenerRegistration = db.collection("users")
            .document(userId)
            .collection("dictionaries")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val dictionaries = snapshot?.toObjects(Dictionary::class.java) ?: emptyList()
                trySend(dictionaries).isSuccess
            }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    suspend fun addDictionary(dictionary: Dictionary) {
        db.collection("users").document(userId).collection("dictionaries").add(dictionary)
            .await()
    }

    suspend fun addWordToDictionary(dictionaryUuid: String, wordId: String) {
        try {
            val dictionaryQuerySnapshot = db.collection("users")
                .document(userId)
                .collection("dictionaries")
                .whereEqualTo("uuid", dictionaryUuid)
                .get()
                .await()

            if (!dictionaryQuerySnapshot.isEmpty) {
                val dictionaryDocument = dictionaryQuerySnapshot.documents.first()
                val dictionaryRef = dictionaryDocument.reference

                dictionaryRef.update("words", FieldValue.arrayUnion(wordId))
                    .await()
            } else {
                Log.d(
                    "addWordToDictionary",
                    "Nenhum dicionário encontrado com o UUID: $dictionaryUuid"
                )
            }
        } catch (e: Exception) {
            Log.e("addWordToDictionary", "Erro ao adicionar palavra ao dicionário: ${e.message}", e)
        }
    }


    private suspend fun getWordbyId(wordId: String): Word? {
        return try {
            val wordSnapshot = db.collection("words").document(wordId).get().await()
            wordSnapshot.toObject(Word::class.java)
        } catch (e: Exception) {
            Log.e("getWordById", "Erro ao recuperar a palavra com ID $wordId: ${e.message}", e)
            null
        }
    }
}