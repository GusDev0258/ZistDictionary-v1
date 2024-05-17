package udesc.eso.ddm.viewmodel

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

    fun translateAndSaveWord(request: TranslationRequest) {
        viewModelScope.launch {
            val existingWord =
                wordRepository.getWord(request.word, request.fromLanguage, request.toLanguage)
            if (existingWord != null) {
                _translatedWord.value = existingWord
            } else {
                val translatedWord = wordRepository.translateWord(request)
                val wordId = wordRepository.saveWordToDb(translatedWord)
                _translatedWord.value = translatedWord.copy(uuid = wordId)
            }
        }
    }
}