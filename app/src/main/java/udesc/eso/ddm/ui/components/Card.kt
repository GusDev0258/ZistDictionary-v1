package udesc.eso.ddm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import udesc.eso.ddm.ui.theme.Black
import udesc.eso.ddm.ui.theme.BlueGradient

@Composable
fun NavigationCard(title: String, navigationRoute: String, navController: NavController) {
    Button(
        onClick = { navController.navigate(navigationRoute) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .background(BlueGradient, RoundedCornerShape(12.dp))
            .height(90.dp)
            .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = Black)
    ) {
        Text(title)
    }
}

@Composable
fun CreateNewDictionaryButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Black,
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .height(90.dp)
            .width(90.dp)
            .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = Black),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "Add New Card Button")
            Text("Novo", fontWeight = FontWeight.Bold)
        }
    }
}