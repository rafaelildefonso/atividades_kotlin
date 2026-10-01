package com.rafaelildefonso.rickyandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rafaelildefonso.rickyandmorty.data.CharacterRepository
import com.rafaelildefonso.rickyandmorty.domain.model.CharacterUiState
import com.rafaelildefonso.rickyandmorty.domain.model.SyncState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)

    val uiState: StateFlow<CharacterUiState> = combine(
        repository.characters,
        _syncState
    ) { characters, syncState ->
        CharacterUiState(
            characters = characters,
            syncState = syncState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CharacterUiState()
    )

    fun syncCharacters() {
        viewModelScope.launch {
            _syncState.update { SyncState.Loading }
            repository.syncCharacters()
                .onSuccess { total ->
                    _syncState.update { SyncState.Success(total) }
                }
                .onFailure { error ->
                    val message = error.localizedMessage
                        ?: error.message
                        ?: "Erro desconhecido ao sincronizar os personagens."
                    _syncState.update { SyncState.Error(message) }
                }
        }
    }

    companion object {
        fun provideFactory(repository: CharacterRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
                        return CharacterViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}