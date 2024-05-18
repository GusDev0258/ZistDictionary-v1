package udesc.eso.ddm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import udesc.eso.ddm.viewmodel.DictionaryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDictionaryModal(
    viewModel: DictionaryViewModel,
    onDismiss: () -> Unit
) {
    val model = viewModel.createDictionaryModel.collectAsState()
    var isFromLanguageExpanded by remember { mutableStateOf(false) }
    var isToLanguageExpanded by remember { mutableStateOf(false) }
    var isDropdownExpanded by remember {
        mutableStateOf(false)
    }
    val availableLanguages = model.value.availableLanguages
    val languages = listOf("Portuguese", "English", "Spanish", "Deutsch")
    var selectedFromLanguage by remember {
        mutableStateOf("")
    }

    var selectedToLanguage by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp,
            shadowElevation = 8.dp,
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = model.value.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExposedDropdownMenuBox(expanded = isFromLanguageExpanded, onExpandedChange = {
                        isFromLanguageExpanded = !isFromLanguageExpanded
                    }) {
                        TextField(
                            value = selectedFromLanguage,
                            onValueChange = {},
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isFromLanguageExpanded)
                            })
                        ExposedDropdownMenu(
                            expanded = isFromLanguageExpanded,
                            onDismissRequest = { isFromLanguageExpanded = false }) {
                            languages.forEachIndexed { index, language ->
                                DropdownMenuItem(
                                    text = { Text(text = language) },
                                    onClick = {
                                        selectedFromLanguage = availableLanguages[index]
                                        isFromLanguageExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                    ExposedDropdownMenuBox(expanded = isToLanguageExpanded, onExpandedChange = {
                        isToLanguageExpanded = !isToLanguageExpanded
                    }) {
                        TextField(
                            value = selectedToLanguage,
                            onValueChange = {},
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isToLanguageExpanded)
                            })
                        ExposedDropdownMenu(
                            expanded = isToLanguageExpanded,
                            onDismissRequest = { isToLanguageExpanded = false }) {
                            languages.forEachIndexed { index, language ->
                                DropdownMenuItem(
                                    text = { Text(text = language) },
                                    onClick = {
                                        selectedToLanguage = languages[index]
                                        isToLanguageExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.createDictionary(
                                selectedFromLanguage,
                                selectedToLanguage
                            )
                            viewModel.hideCreateDictionaryModal()
                        },
                    ) {
                        Text("Criar Dicionário")
                    }
                    Button(
                        onClick = onDismiss,
                    ) {
                        Text("Cancelar")
                    }
                }

            }
        }
    }
}
