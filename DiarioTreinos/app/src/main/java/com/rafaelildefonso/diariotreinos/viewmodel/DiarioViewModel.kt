package com.rafaelildefonso.diariotreinos.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rafaelildefonso.diariotreinos.model.Diario

class DiarioViewModel : ViewModel() {

    var estaLogado by mutableStateOf(false)
        private set

    val diarios = mutableStateListOf(
        Diario(1, "Supino", 3, 12),
        Diario(2, "Agachamento", 3, 14),
        Diario(3, "Rosca", 3, 10),
        Diario(4, "Cadeira flexora", 3, 12),
    )

    fun fazerLogin() {
        estaLogado = true
    }

    fun fazerLogout() {
        estaLogado = false
    }

    fun alternarConclusao(diarioId: Int) {
        val index = diarios.indexOfFirst { it.id == diarioId }
        if (index != -1) {
            diarios[index] = diarios[index].copy(concluidoHoje = !diarios[index].concluidoHoje)
        }
    }

    fun buscarDiario(diarioId: Int): Diario? =
        diarios.find { it.id == diarioId }
}