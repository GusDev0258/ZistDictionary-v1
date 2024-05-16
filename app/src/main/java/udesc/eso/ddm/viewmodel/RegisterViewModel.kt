package udesc.eso.ddm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import udesc.eso.ddm.infra.repository.AuthRepository
import udesc.eso.ddm.ui.screens.states.RegisterUiState

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()
    private val _signUpSuccessful = MutableSharedFlow<Boolean>()
    val signUpSuccessful = _signUpSuccessful.asSharedFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { email ->
                    _uiState.update {
                        it.copy(email = email)
                    }
                },
                onPasswordChange = { password ->
                    _uiState.update {
                        it.copy(password = password)
                    }
                },
                onUsernameChange = { username ->
                    _uiState.update {
                        it.copy(username = username)
                    }
                }
            )
        }
    }

    suspend fun signUp() {
        try {
            authRepository.signUp(
                _uiState.value.email,
                _uiState.value.username,
                _uiState.value.password
            )
            _signUpSuccessful.emit(true)
        } catch (e: Exception) {
            Log.e("Register View", "Sign up", e)
            _uiState.update {
                it.copy(
                    error = "Erro ao cadastrar o usuário"
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
        }
    }
}