package com.rafaelildefonso.atividadeapis.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelildefonso.atividadeapis.model.CepResponse
import com.rafaelildefonso.atividadeapis.network.ViaCepClient
import kotlinx.coroutines.launch

sealed interface CepUiState {
    object Idle : CepUiState
    object Loading : CepUiState
    data class Success(val endereco: CepResponse) : CepUiState
    object NotFound : CepUiState
}

class CepViewModel : ViewModel() {

    var cepUiState: CepUiState by mutableStateOf(CepUiState.Idle)
        private set

    fun buscarCep(cep: String) {
        val cleaned = cep.replace("[^0-9]".toRegex(), "")
        if (cleaned.length != 8) return
        viewModelScope.launch {
            cepUiState = CepUiState.Loading
            cepUiState = try {
                val endereco = ViaCepClient.apiService.buscarCep(cleaned)
                if (endereco.erro == true) CepUiState.NotFound
                else CepUiState.Success(endereco)
            } catch (e: Exception) {
                CepUiState.NotFound
            }
        }
    }
}
