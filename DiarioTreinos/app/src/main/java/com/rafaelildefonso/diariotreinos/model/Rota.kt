package com.rafaelildefonso.diariotreinos.model

import kotlinx.serialization.Serializable

sealed interface Rota {
    @Serializable
    data object Splash : Rota

    @Serializable
    data object Login : Rota

    @Serializable
    data object ListaDiarios : Rota

    @Serializable
    data object Estatisticas : Rota

    @Serializable
    data object Perfil : Rota

    @Serializable
    data class DetalheDiario(val diarioId: Int) : Rota
}