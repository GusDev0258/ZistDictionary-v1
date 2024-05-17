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
import udesc.eso.ddm.ui.screens.states.LoginUiState

class LoginViewModel(val authRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    private val _signInSuccessful = MutableSharedFlow<Boolean>()
    val signInSuccessful = _signInSuccessful.asSharedFlow()

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
                }
            )

        }
    }

    suspend fun signIn() {
        try {
            authRepository.signIn(
                _uiState.value.email,
                _uiState.value.password
            )
            _signInSuccessful.emit(true)
        } catch (e: Exception) {
            Log.e("Login view", "Sign in", e)
            _uiState.update {
                it.copy(
                    error = "Problemas ao entrar"
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