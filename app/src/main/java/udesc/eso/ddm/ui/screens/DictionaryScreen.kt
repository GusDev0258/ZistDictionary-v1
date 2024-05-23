package udesc.eso.ddm.ui.screens

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import udesc.eso.ddm.model.TranslationRequest
import udesc.eso.ddm.ui.components.ImageText
import udesc.eso.ddm.ui.components.NavBar
import udesc.eso.ddm.ui.components.WordCard
import udesc.eso.ddm.ui.screens.states.UserUiState
import udesc.eso.ddm.ui.theme.Green50
import udesc.eso.ddm.ui.theme.Green70
import udesc.eso.ddm.ui.theme.Grey10
import udesc.eso.ddm.ui.theme.Orange50
import udesc.eso.ddm.viewmodel.DictionaryViewModel
import udesc.eso.ddm.viewmodel.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryScreen(
    userUiState: UserUiState,
    wordViewModel: WordViewModel,
    dictionaryViewModel: DictionaryViewModel,
    dictionaryId: String?,
    navController: NavController
) {
    val id = dictionaryId ?: throw Exception("Id não passado")
    val currentDictionary by dictionaryViewModel.currentDictionary.collectAsState()
    var newWord by remember { mutableStateOf("") }
    val currentWord by wordViewModel.translatedWord.collectAsState()
    val words by dictionaryViewModel.dictionaryWords.collectAsState()
    val isTranslating by wordViewModel.isTranslating.collectAsState()

    LaunchedEffect(id) {
        dictionaryViewModel.getDictionaryById(id)
        dictionaryViewModel.loadDictionaryWordsRealtime(id)
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
                    Text("Olá ${userUiState.username}", fontSize = 14.sp)
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
        content = { padding ->
            if (currentDictionary != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = "${currentDictionary?.fromLanguage} - ${
                            currentDictionary?.toLanguage
                        }",
                        fontWeight = FontWeight.Bold, fontSize = 24.sp
                    )

                    OutlinedTextField(
                        value = newWord,
                        onValueChange = { newWord = it },
                        label = { Text("Nova Palavra") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Green70,
                            unfocusedBorderColor = Green50
                        )
                    )

                    Button(
                        onClick = {
                            Log.d(
                                "Dictionary languages", "${currentDictionary?.fromLanguage} - " +
                                        "${currentDictionary?.toLanguage} "
                            )
                            val request = TranslationRequest(
                                word = newWord,
                                fromLanguage = currentDictionary?.fromLanguage ?: "",
                                toLanguage = currentDictionary?.toLanguage ?: ""
                            )
                            wordViewModel.translateAndSaveWord(request)
                            newWord = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Orange50
                        )
                    ) {
                        Text("Adicionar Palavra", fontWeight = FontWeight.Bold)
                    }
                    LaunchedEffect(currentWord) {
                        val word = wordViewModel.translatedWord.value
                        if (word != null) {
                            dictionaryViewModel.addWordToDictionary(id, word.uuid)
                        }
                    }
                    LazyColumn(
                        userScrollEnabled = true,
                        modifier = Modifier.padding(20.dp).fillMaxSize(1f),
                    ) {
                        items(words.size) { index ->
                            WordCard(word = words[index])
                        }
                    }
                }
            } else {
                Text(text = "Loading...")
            }
        }
    )
    if (isTranslating) {
        TranslationDialog(isTranslating)
    }
}

@Composable
fun TranslationDialog(active: Boolean) {
    val dots = listOf(".", "..", "...")
    var currentDotIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (active) {
            kotlinx.coroutines.delay(500)
            currentDotIndex = (currentDotIndex + 1) % dots.size
        }
    }

    Dialog(onDismissRequest = {}) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Traduzindo palavra${dots[currentDotIndex]}", fontSize = 18.sp)
        }
    }
}