package udesc.eso.ddm.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import udesc.eso.ddm.infra.repository.AuthRepository
import udesc.eso.ddm.navigation.Routes
import udesc.eso.ddm.ui.components.ImageLogo
import udesc.eso.ddm.ui.components.PasswordInputField
import udesc.eso.ddm.ui.components.PrimaryButton
import udesc.eso.ddm.ui.components.SecondaryButton
import udesc.eso.ddm.ui.components.TextInputField
import udesc.eso.ddm.ui.screens.states.RegisterUiState

@Composable
fun RegisterScreen(uiState: RegisterUiState, onSignUpClick: () -> Unit) {

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        AnimatedVisibility(visible = uiState.error != null) {
            uiState.error?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(Color.Red)
                        .height(40.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Row {
            ImageLogo()
        }
        Column {
            TextInputField(
                fieldLabel = "e-mail",
                inputText = uiState.email,
                onInputTextChange = uiState.onEmailChange
            )
            TextInputField(
                fieldLabel = "username",
                inputText = uiState.username,
                onInputTextChange = uiState.onUsernameChange
            )
            PasswordInputField(
                passwordText = uiState.password,
                onPasswordTextChange = uiState.onPasswordChange
            )
        }
        Column {
            PrimaryButton(
                buttonText = "CRIAR CONTA", handleClick = onSignUpClick
            )
        }
    }
}