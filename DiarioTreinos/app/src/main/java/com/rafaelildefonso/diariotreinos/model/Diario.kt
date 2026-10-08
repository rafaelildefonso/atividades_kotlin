package com.rafaelildefonso.diariotreinos.model

data class Diario(
    val id: Int,
    val nome: String,
    val series: Int,
    val repeticoes: Int,
    val concluidoHoje: Boolean = false
)