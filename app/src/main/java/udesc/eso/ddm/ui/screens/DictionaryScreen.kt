package udesc.eso.ddm.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DictionaryScreen(dictionaryId: String?, navController: NavController) {
    Text("Funfou $dictionaryId")
}