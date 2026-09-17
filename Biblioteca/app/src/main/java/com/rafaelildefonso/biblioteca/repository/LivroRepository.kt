package com.rafaelildefonso.biblioteca.repository

import android.content.ContentValues
import com.rafaelildefonso.biblioteca.database.DatabaseHelper
import com.rafaelildefonso.biblioteca.model.Livro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LivroRepository(private val dbHelper: DatabaseHelper) {

    suspend fun inserir(livro: Livro) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put(DatabaseHelper.COL_TITULO, livro.titulo)
            put(DatabaseHelper.COL_AUTOR, livro.autor)
            put(DatabaseHelper.COL_ANO, livro.ano)
        }
        db.insert(DatabaseHelper.TABLE_LIVROS, null, valores)
    }

    suspend fun listarTodos(): List<Livro> = withContext(Dispatchers.IO) {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_LIVROS,
            null, null, null, null, null,
            "${DatabaseHelper.COL_ANO} DESC"
        )
        val livros = mutableListOf<Livro>()
        cursor.use {
            while (it.moveToNext()) {
                livros.add(
                    Livro(
                        id = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COL_ID)),
                        titulo = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_TITULO)),
                        autor = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_AUTOR)),
                        ano = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COL_ANO))
                    )
                )
            }
        }
        livros
    }
}
