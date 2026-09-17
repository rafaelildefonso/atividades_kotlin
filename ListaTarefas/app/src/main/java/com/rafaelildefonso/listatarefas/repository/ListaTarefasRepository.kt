package com.rafaelildefonso.listatarefas.repository

import android.content.ContentValues
import com.rafaelildefonso.listatarefas.database.DatabaseHelper
import com.rafaelildefonso.listatarefas.model.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListaTarefasRepository(private val dbHelper: DatabaseHelper) {

    suspend fun inserir(livro: Tarefa) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put(DatabaseHelper.COL_TITULO, livro.titulo)
            put(DatabaseHelper.COL_DESCRICAO, livro.descricao)
            put(DatabaseHelper.COL_STATUS, livro.status)
        }
        db.insert(DatabaseHelper.TABLE_TAREFAS, null, valores)
    }

    suspend fun listarTodos(): List<Tarefa> = withContext(Dispatchers.IO) {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_TAREFAS,
            null, null, null, null, null,
            "${DatabaseHelper.COL_STATUS} DESC"
        )
        val tarefas = mutableListOf<Tarefa>()
        cursor.use {
            while (it.moveToNext()) {
                tarefas.add(
                    Tarefa(
                        id = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COL_ID)),
                        titulo = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_TITULO)),
                        descricao = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_DESCRICAO)),
                        status = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_STATUS))
                    )
                )
            }
        }
        tarefas
    }
}
