package com.rafaelildefonso.listatarefas.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelildefonso.listatarefas.database.DatabaseHelper
import com.rafaelildefonso.listatarefas.model.Tarefa
import com.rafaelildefonso.listatarefas.repository.ListaTarefasRepository
import kotlinx.coroutines.launch

sealed interface ListaTarefasUiState {
    object Loading : ListaTarefasUiState
    data class Success(val tarefas: List<Tarefa>) : ListaTarefasUiState
    data class Error(val message: String) : ListaTarefasUiState
}

class ListaTarefasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ListaTarefasRepository(DatabaseHelper(application))

    var uiState: ListaTarefasUiState by mutableStateOf(ListaTarefasUiState.Loading)
        private set

    init { carregarTarefas() }

    fun carregarTarefas() {
        viewModelScope.launch {
            uiState = ListaTarefasUiState.Loading
            uiState = try {
                ListaTarefasUiState.Success(repository.listarTodos())
            } catch (e: Exception) {
                ListaTarefasUiState.Error(e.message ?: "Erro ao acessar o banco de dados.")
            }
        }
    }

    fun salvarTarefa(titulo: String, descricao: String, status: String) {
        viewModelScope.launch {
            repository.inserir(Tarefa(titulo = titulo, descricao = descricao, status = status))
            carregarTarefas()
        }
    }
}