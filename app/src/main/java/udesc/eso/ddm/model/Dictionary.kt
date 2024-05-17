package udesc.eso.ddm.model

import kotlinx.serialization.Serializable

@Serializable
data class Dictionary(
    val uuid: String = "",
    val fromLanguage: String,
    val toLanguage: String,
    val words: List<String> = listOf()
)

data class CreateDictionaryModel(
    val title: String = "Novo Dicionário",
    val fromLanguage: String = "",
    val toLanguage: String = "",
    val availableLanguages: List<String> = listOf("Portuguese", "English", "Spanish", "Deutsch")
)
