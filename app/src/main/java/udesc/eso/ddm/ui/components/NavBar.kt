package udesc.eso.ddm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import compose.icons.FeatherIcons
import compose.icons.feathericons.BookOpen
import compose.icons.feathericons.Bookmark
import compose.icons.feathericons.Copy
import compose.icons.feathericons.Home
import compose.icons.feathericons.User
import udesc.eso.ddm.navigation.Routes

@Composable
fun NavBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.APP_HOME) },
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = FeatherIcons.Copy,
                        contentDescription = "Cards"
                    )
                    Text(text = "Cards", fontSize = 12.sp)
                }
            },
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.COLLECTIONS_SCREEN) },
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = FeatherIcons.Bookmark,
                        contentDescription = "Coleções"
                    )
                    Text(text = "Coleções", fontSize = 12.sp)
                }
            },
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.APP_HOME) },
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = FeatherIcons.Home,
                        contentDescription = "Home"
                    )
                    Text(text = "Home", fontSize = 12.sp)
                }
            },
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.APP_HOME) },
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = FeatherIcons.BookOpen,
                        contentDescription = "Dicionários"
                    )
                    Text(text = "Dicionários", fontSize = 12.sp)
                }
            },
        )
        NavigationBarItem(
                selected = false,
        onClick = { navController.navigate(Routes.APP_HOME) },
        icon = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    imageVector = FeatherIcons.User,
                    contentDescription = "Conta"
                )
                Text(text = "Conta", fontSize = 12.sp)
            }
        },
        )
    }
}