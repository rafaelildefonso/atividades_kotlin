package com.rafaelildefonso.listatarefas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelildefonso.listatarefas.database.AppDatabase
import com.rafaelildefonso.listatarefas.model.Tarefa
import com.rafaelildefonso.listatarefas.network.JsonPlaceholderApi
import com.rafaelildefonso.listatarefas.repository.ListaTarefasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface SyncStatus {
    data object Idle : SyncStatus
    data object Loading : SyncStatus
    data class Error(val message: String) : SyncStatus
}

class ListaTarefasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ListaTarefasRepository(
        dao = AppDatabase.get(application).tarefaDao(),
        api = JsonPlaceholderApi.create()
    )

    val tarefas: StateFlow<List<Tarefa>> = repository.observarTarefas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.Idle)
    val syncStatus: StateFlow<SyncStatus> = _syncStatus.asStateFlow()

    fun sincronizar() {
        if (_syncStatus.value is SyncStatus.Loading) return
        viewModelScope.launch {
            _syncStatus.value = SyncStatus.Loading
            try {
                repository.sincronizarTarefas()
                _syncStatus.value = SyncStatus.Idle
            } catch (e: Exception) {
                _syncStatus.value = SyncStatus.Error(
                    e.message ?: "Falha ao sincronizar com a API."
                )
            }
        }
    }

    fun salvarTarefa(titulo: String, descricao: String, concluido: Boolean) {
        if (titulo.isBlank()) return
        viewModelScope.launch {
            repository.inserirTarefa(
                Tarefa(
                    titulo = titulo.trim(),
                    descricao = descricao.trim(),
                    concluido = concluido
                )
            )
        }
    }

    fun limparErro() {
        if (_syncStatus.value is SyncStatus.Error) {
            _syncStatus.value = SyncStatus.Idle
        }
    }
}
