package udesc.eso.ddm.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import udesc.eso.ddm.model.Dictionary
import udesc.eso.ddm.navigation.Routes
import udesc.eso.ddm.ui.components.CreateDictionaryModal
import udesc.eso.ddm.ui.components.CreateNewDictionaryButton
import udesc.eso.ddm.ui.components.ImageText
import udesc.eso.ddm.ui.components.NavBar
import udesc.eso.ddm.ui.components.NavigationCard
import udesc.eso.ddm.ui.screens.states.UserUiState
import udesc.eso.ddm.ui.theme.Black
import udesc.eso.ddm.ui.theme.Grey10
import udesc.eso.ddm.viewmodel.DictionaryViewModel

@ExperimentalMaterial3Api
@Composable
fun AppHomeScreen(
    uiState: UserUiState,
    navController: NavController,
    dictionaryViewModel: DictionaryViewModel
) {
    val dictionaryList by dictionaryViewModel.dictionaries.collectAsState()
    val showCreateDictionaryModal by dictionaryViewModel.showCreateDictionaryModal.collectAsState()
    LaunchedEffect(key1 = "dictionaries") {
        dictionaryViewModel.loadDictionaries()
    }
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
                    Text("Olá ${uiState.username}", fontSize = 14.sp)
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SectionRow(
                    dictionaryList = dictionaryList,
                    navController = navController,
                    onCreateNewDictionaryClick = {
                        dictionaryViewModel.showCreateDictionaryModal()
                    }
                )
                if (showCreateDictionaryModal) {
                    CreateDictionaryModal(
                        viewModel = dictionaryViewModel,
                        onDismiss = {
                            dictionaryViewModel.hideCreateDictionaryModal()
                        },
                    )
                }
            }
        }
    )
}

@Composable
fun SectionRow(
    dictionaryList: List<Dictionary>,
    navController: NavController,
    onCreateNewDictionaryClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Dicionários",
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
                CreateNewDictionaryButton(
                    onClick = onCreateNewDictionaryClick
                )
            }
            items(dictionaryList.size) { index ->
                val dictionary = dictionaryList[index]
                Log.d("Dictionary", "Dictionary: ${dictionary}")
                NavigationCard(
                    title = dictionary.fromLanguage,
                    navigationRoute = "${Routes.DICTIONARY_SCREEN}/${
                        dictionary.uuid
                    }",
                    navController = navController
                )
            }
        }
    }
}