package udesc.eso.ddm.model

import kotlinx.serialization.Serializable

@Serializable
data class TranslationRequest(
    private val word: String,
    private val incomeLanguage: String,
    private val outcomeLanguage: String
)

@Serializable
data class TranslationResponse(
    private val fromLanguage: String,
    private val toLanguage: String,
    private val word: String,
    private val translatedWord: String,
    private val meaning: String,
    private val fromLanguageExampleSentence: String,
    private val toLanguageExampleSentence: String,
)
