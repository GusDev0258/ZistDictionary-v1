package udesc.eso.ddm.ui.screens.states

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val onEmailChange: (String) -> Unit= {},
    val onPasswordChange: (String) -> Unit = {},
    val onUsernameChange: (String) -> Unit = {},
    val error: String? = null
)
