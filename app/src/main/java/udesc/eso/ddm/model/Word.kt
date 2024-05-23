package udesc.eso.ddm.model

import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val uuid: String = "",
    val word: String = "",
    val fromLanguage: String = "",
    val toLanguage: String = "",
    val meaning: String = "",
    val translation: String = "",
    val fromLanguageExampleSentence: String = "",
    val toLanguageExampleSentence: String = "",
)

