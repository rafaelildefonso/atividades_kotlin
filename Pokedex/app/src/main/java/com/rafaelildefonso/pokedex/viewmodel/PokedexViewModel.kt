package com.rafaelildefonso.pokedex.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelildefonso.pokedex.model.PokemonDetailResponse
import com.rafaelildefonso.pokedex.network.RetrofitClient
import kotlinx.coroutines.launch

sealed interface PokedexUiState {
    object Loading : PokedexUiState
    data class Success(val pokemon: PokemonDetailResponse) : PokedexUiState
    object NotFound : PokedexUiState
}

class PokedexViewModel : ViewModel() {

    var uiState: PokedexUiState by mutableStateOf(PokedexUiState.Loading)
        private set

    private var currentId = 1

    init {
        buscar(currentId.toString())
    }

    fun buscar(termo: String) {
        if (termo.isBlank()) return
        viewModelScope.launch {
            uiState = PokedexUiState.Loading
            uiState = try {
                val pokemon = RetrofitClient.apiService.getPokemon(termo.lowercase())
                currentId = pokemon.id
                PokedexUiState.Success(pokemon)
            } catch (e: Exception) {
                PokedexUiState.NotFound
            }
        }
    }

    fun anterior() {
        if (currentId > 1) buscar((currentId - 1).toString())
    }

    fun proximo() {
        buscar((currentId + 1).toString())
    }
}