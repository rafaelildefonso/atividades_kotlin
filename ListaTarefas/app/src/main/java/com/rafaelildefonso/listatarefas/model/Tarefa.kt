package com.rafaelildefonso.listatarefas.model

data class Tarefa(
    val id: Long = 0,
    val titulo: String,
    val descricao: String,
    val status: String
)