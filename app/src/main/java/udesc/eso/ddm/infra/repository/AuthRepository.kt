package udesc.eso.ddm.infra.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import udesc.eso.ddm.model.User

class AuthRepository(private val auth: FirebaseAuth, private val store: FirebaseFirestore) {
    private var currentUser: User? = null
    suspend fun signUp(email: String, username: String, password: String) {
        val userRegistered = auth.createUserWithEmailAndPassword(email, password).await()
        if (userRegistered.user != null) {
            val user = userRegistered.user;
            val userData = User(uuid = user!!.uid, email = email, username = username)
            currentUser = userData;
            store.collection("users").document(user.uid).set(userData).await()
        }
    }

    suspend fun signIn(email: String, password: String) {
        val userLogged = auth.signInWithEmailAndPassword(email, password).await()
        if (userLogged != null) {
            currentUser = getUserByEmail(userLogged.user?.email)
        }
    }

    suspend fun getUserByEmail(email: String?): User? {
        val querySnapshot = store
            .collection("users")
            .whereEqualTo("email", email)
            .get()
            .await()

        return if (!querySnapshot.isEmpty) {
            querySnapshot.documents.first().toObject(User::class.java)
        } else {
            null
        }
    }

    fun getCurrentUser(): User? {
        if (currentUser != null) {
            return currentUser
        }
        return null
    }
}