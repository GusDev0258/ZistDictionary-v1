package udesc.eso.ddm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import udesc.eso.ddm.infra.repository.AuthRepository
import udesc.eso.ddm.infra.repository.UserRepository
import udesc.eso.ddm.ui.screens.states.UserUiState

class UserViewModel(userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()
    init {
        _uiState.update {
            currentState ->
            currentState.copy(
                email = userRepository.getCurrentUserEmail(),
                username = userRepository.getCurrentUserUsername(),
                uuid = userRepository.getCurrentUserId()
            )
        }
    }
}