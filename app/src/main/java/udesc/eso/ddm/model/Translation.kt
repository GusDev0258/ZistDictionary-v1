package udesc.eso.ddm.model

import kotlinx.serialization.Serializable

@Serializable
data class TranslationRequest(
    val word: String,
    val fromLanguage: String,
    val toLanguage: String
)

@Serializable
data class TranslationResponse(
    val word: String = "",
    val from: String = "",
    val to: String = "",
    val translation: String = "",
    val meaning: String = "",
    val fromLanguageExampleSentence: String = "",
    val toLanguageExampleSentence: String = "",
)