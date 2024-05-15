package udesc.eso.ddm.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import udesc.eso.ddm.testFun
import udesc.eso.ddm.ui.components.ImageLogo
import udesc.eso.ddm.ui.components.ImageText
import udesc.eso.ddm.ui.components.PrimaryButton
import udesc.eso.ddm.ui.components.SecondaryButton
import udesc.eso.ddm.ui.theme.Green40
import udesc.eso.ddm.ui.theme.Orange30

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        ImageText()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ImageLogo()
            Text("Pronto para aprender?", color = Green40, fontWeight = FontWeight.Bold)
            Text("Junte-se a nós!", color = Orange30, fontWeight = FontWeight.Bold)
        }
        Column {
            PrimaryButton(buttonText = "CRIAR CONTA", handleClick = {navController.navigate("REGISTER")})
            SecondaryButton(buttonText = "ENTRAR", handleClick = {navController.navigate("LOGIN")})
        }

    }
}