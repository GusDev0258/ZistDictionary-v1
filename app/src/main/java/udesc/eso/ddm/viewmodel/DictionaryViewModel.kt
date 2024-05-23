package udesc.eso.ddm.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import udesc.eso.ddm.infra.repository.DictionaryRepository
import udesc.eso.ddm.infra.repository.UserRepository
import udesc.eso.ddm.model.CreateDictionaryModel
import udesc.eso.ddm.model.Dictionary
import udesc.eso.ddm.model.Word
import java.util.UUID
import kotlin.reflect.KProperty

class DictionaryViewModel(private val dictionaryRepository: DictionaryRepository) : ViewModel() {
    private val _dictionaries = MutableStateFlow<List<Dictionary>>(emptyList())
    val dictionaries: StateFlow<List<Dictionary>> = _dictionaries
    private val _createDictionaryModel = MutableStateFlow(CreateDictionaryModel())
    val createDictionaryModel: StateFlow<CreateDictionaryModel> = _createDictionaryModel
    private val _showCreateDictionaryModal = MutableStateFlow<Boolean>(false)
    val showCreateDictionaryModal = _showCreateDictionaryModal
    private val _currentDictionary = MutableStateFlow<Dictionary?>(null)
    val currentDictionary = _currentDictionary

    private val _dictionaryWords = MutableStateFlow<List<Word?>>(emptyList())
    val dictionaryWords: StateFlow<List<Word?>> get() = _dictionaryWords

//    fun loadDictionaryWords(dictionaryUuid: String) {
//        viewModelScope.launch {
//            val words = dictionaryRepository.loadDictionaryWords(dictionaryUuid)
//            _dictionaryWords.value = words ?: emptyFlow()
//        }
//    }
    fun loadDictionaryWordsRealtime(dictionaryUuid: String) {
        dictionaryRepository.loadDictionaryWordsRealtime(dictionaryUuid) { words ->
            _dictionaryWords.value = words
        }
    }
    fun showCreateDictionaryModal() {
        _showCreateDictionaryModal.value = true
    }

    fun hideCreateDictionaryModal() {
        _showCreateDictionaryModal.value = false
    }

    fun createDictionary(selectedFromLanguage: String, selectedToLanguage: String) {
        viewModelScope.launch {
            val dictionary =
                Dictionary(
                    uuid = UUID.randomUUID().toString(),
                    selectedFromLanguage,
                    selectedToLanguage
                )
            dictionaryRepository.addDictionary(dictionary)
            _createDictionaryModel.value = CreateDictionaryModel()
        }
    }

    fun loadDictionaries() {
        viewModelScope.launch {
            dictionaryRepository.getUserDictionaries().collect { dictionaryList ->
                _dictionaries.value = dictionaryList
            }
        }
    }

    fun addWordToDictionary(dictionaryUuid: String, wordId: String) {
        viewModelScope.launch {
            dictionaryRepository.addWordToDictionary(dictionaryUuid, wordId)
        }
    }

    fun getDictionaryById(id: String) {
        viewModelScope.launch {
            val dictionary = dictionaryRepository.getUserDictionaryById(id)
            _currentDictionary.value = dictionary
        }
    }
}