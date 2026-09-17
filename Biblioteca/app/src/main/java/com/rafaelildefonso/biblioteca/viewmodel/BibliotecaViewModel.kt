package com.rafaelildefonso.biblioteca.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelildefonso.biblioteca.database.DatabaseHelper
import com.rafaelildefonso.biblioteca.model.Livro
import com.rafaelildefonso.biblioteca.repository.LivroRepository
import kotlinx.coroutines.launch

sealed interface BibliotecaUiState {
    object Loading : BibliotecaUiState
    data class Success(val livros: List<Livro>) : BibliotecaUiState
    data class Error(val message: String) : BibliotecaUiState
}

class BibliotecaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LivroRepository(DatabaseHelper(application))

    var uiState: BibliotecaUiState by mutableStateOf(BibliotecaUiState.Loading)
        private set

    init { carregarLivros() }

    fun carregarLivros() {
        viewModelScope.launch {
            uiState = BibliotecaUiState.Loading
            uiState = try {
                BibliotecaUiState.Success(repository.listarTodos())
            } catch (e: Exception) {
                BibliotecaUiState.Error(e.message ?: "Erro ao acessar o banco de dados.")
            }
        }
    }

    fun salvarLivro(titulo: String, autor: String, ano: Int) {
        viewModelScope.launch {
            repository.inserir(Livro(titulo = titulo, autor = autor, ano = ano))
            carregarLivros()
        }
    }
}