package com.rafaelildefonso.listatarefas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tarefas",
    indices = [Index(value = ["remoteId"], unique = true)]
)
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titulo: String,
    val descricao: String = "",
    val concluido: Boolean = false,
    @ColumnInfo(index = true)
    val remoteId: Int? = null
)
