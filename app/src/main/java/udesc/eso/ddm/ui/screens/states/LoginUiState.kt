package udesc.eso.ddm.ui.screens.states

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val error: String? = null

)
