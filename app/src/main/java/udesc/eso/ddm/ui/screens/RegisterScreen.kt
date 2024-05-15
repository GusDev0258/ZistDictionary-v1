package udesc.eso.ddm.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import udesc.eso.ddm.ui.components.ImageLogo
import udesc.eso.ddm.ui.components.PasswordInputField
import udesc.eso.ddm.ui.components.PrimaryButton
import udesc.eso.ddm.ui.components.SecondaryButton
import udesc.eso.ddm.ui.components.TextInputField

@Composable
fun RegisterScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(12.dp).fillMaxSize()
    ) {
        Row {
            ImageLogo()
        }
        Column {
            TextInputField(fieldLabel = "e-mail")
            TextInputField(fieldLabel = "username")
            PasswordInputField()
        }
        Column {
            PrimaryButton(buttonText = "CRIAR CONTA", handleClick = {navController.navigate("HOME")})
            SecondaryButton(buttonText = "JÁ TENHO UMA CONTA", handleClick = {navController.navigate("LOGIN")})
        }
    }
}