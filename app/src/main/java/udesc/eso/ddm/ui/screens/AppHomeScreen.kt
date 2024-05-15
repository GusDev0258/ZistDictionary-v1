package udesc.eso.ddm.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import compose.icons.FeatherIcons
import compose.icons.feathericons.Bookmark
import compose.icons.feathericons.Home
import udesc.eso.ddm.navigation.Routes
import udesc.eso.ddm.ui.components.CreateNewCard
import udesc.eso.ddm.ui.components.ImageText
import udesc.eso.ddm.ui.components.NavBar
import udesc.eso.ddm.ui.components.NavigationCard
import udesc.eso.ddm.ui.theme.Black
import udesc.eso.ddm.ui.theme.Grey10

@ExperimentalMaterial3Api
@Composable
fun AppHomeScreen(navController: NavController) {
    val username = "Usuário"
    val dictionaryList = listOf(
        "English",
        "German",
        "Portuguese",
        "Italian",
        "Spanish",
        "Greek",
        "Chinese",
        "Japanese"
    )

    val topicList = listOf("Dicionários", "Flash Cards", "Coleções")
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    ImageText()
                    Text("Olá $username", fontSize = 14.sp)
                }
            }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Grey10,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                ) {
                NavBar(navController = navController)
            }
        },
        content = { it ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(topicList.size) {
                    SectionRow(
                        title = topicList[it],
                        itemTitleList = dictionaryList,
                        navController = navController
                    )
                }
            }

        }

    )
}

@Composable
fun SectionRow(title: String, itemTitleList: List<String>, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                color = Black,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow Forward"
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = true,
        ) {
            item {
                CreateNewCard(
                    navigationRoute = Routes.CREATE_NEW_DICTIONARY,
                    navController = navController
                )
            }
            items(itemTitleList.size) {
                NavigationCard(
                    title = itemTitleList[it],
                    navigationRoute = "${Routes.DICTIONARY_SCREEN}/${itemTitleList[it]}",
                    navController = navController
                )
            }
        }
    }
}