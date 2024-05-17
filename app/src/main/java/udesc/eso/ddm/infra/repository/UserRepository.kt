package udesc.eso.ddm.infra.repository

import udesc.eso.ddm.model.User

class UserRepository(private val authRepository: AuthRepository) {
    private var currentUser: User? = null

    init {
        currentUser = authRepository.getCurrentUser()
    }

    fun getCurrentUserId(): String? {
        if (this.currentUser != null) {
            return this.currentUser?.uuid
        }
        return null;
    }

    fun getCurrentUser(): User? {
        return this.currentUser;
    }

    fun getCurrentUserEmail(): String? {
        return this.currentUser?.email
    }

    fun getCurrentUserUsername(): String? {
        return this.currentUser
            ?.username
    }
}