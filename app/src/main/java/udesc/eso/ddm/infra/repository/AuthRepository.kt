package udesc.eso.ddm.infra.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import udesc.eso.ddm.model.User

class AuthRepository(private val auth: FirebaseAuth, private val store: FirebaseFirestore) {
    suspend fun signUp(email: String, username: String, password: String) {
        val userRegistered = auth.createUserWithEmailAndPassword(email, password).await()
        if (userRegistered.user != null) {
            val user = userRegistered.user;
            val userData = User(uuid = user!!.uid, email = email, username = username)
            store.collection("users").document(user.uid).set(userData).await()
        }
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }
}