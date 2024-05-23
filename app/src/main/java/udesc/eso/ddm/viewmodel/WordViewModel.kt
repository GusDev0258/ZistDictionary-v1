package udesc.eso.ddm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import udesc.eso.ddm.infra.repository.WordRepository
import udesc.eso.ddm.model.TranslationRequest
import udesc.eso.ddm.model.Word

class WordViewModel(private val wordRepository: WordRepository) : ViewModel() {
    private val _translatedWord = MutableStateFlow<Word?>(null)
    val translatedWord: StateFlow<Word?> = _translatedWord

    private val _isTranslating = MutableStateFlow(false)
    val isTranslating: StateFlow<Boolean> get() = _isTranslating

    fun translateAndSaveWord(request: TranslationRequest) {
        var id: String? = null
        viewModelScope.launch {
            try {
                _isTranslating.value = true
                val existingWord =
                    wordRepository.getWord(request.word, request.fromLanguage, request.toLanguage)
                if (existingWord != null) {
                    _translatedWord.value = existingWord
                    _isTranslating.value = false
                } else {
                    Log.d("Translation", "Translating word: ${request.word}")
                    val translatedWord = wordRepository.translateWord(request)
                    Log.d("Translation", "Translation result: $translatedWord")
                    val wordId = wordRepository.saveWordToDb(translatedWord)
                    _translatedWord.value = translatedWord.copy(uuid = wordId)
                    Log.d("Translation", "Word saved with ID: $wordId")
                    _isTranslating.value = false
                }
            } catch (e: Exception) {
                Log.e("TranslationError", "Erro ao traduzir a palavra: ${e.message}")
                _isTranslating.value = false
            }
        }
    }

}