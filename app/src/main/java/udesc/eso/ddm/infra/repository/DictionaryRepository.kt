package udesc.eso.ddm.infra.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import udesc.eso.ddm.model.Dictionary

class DictionaryRepository(
    private val db: FirebaseFirestore,
    private val userRepository: UserRepository
) {
    private val userId =
        userRepository.getCurrentUserId() ?: throw IllegalStateException("User not logged in")

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

    suspend fun addWordToDictionary(dictionaryId: String, wordId: String) {
        val dictionaryRef = db.collection("users").document(userId).collection("dictionaries")
            .document(dictionaryId)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(dictionaryRef)
            val dictionary = snapshot.toObject(Dictionary::class.java)
            if (dictionary != null) {
                val updatedWords = dictionary.words.toMutableList().apply { add(wordId) }
                transaction.update(dictionaryRef, "words", updatedWords)
            }
        }.await()
    }
}