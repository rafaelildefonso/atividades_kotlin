package com.rafaelildefonso.listatarefas.repository

import com.rafaelildefonso.listatarefas.database.TarefaDao
import com.rafaelildefonso.listatarefas.model.Tarefa
import com.rafaelildefonso.listatarefas.network.JsonPlaceholderApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ListaTarefasRepository(
    private val dao: TarefaDao,
    private val api: JsonPlaceholderApi
) {

    fun observarTarefas(): Flow<List<Tarefa>> = dao.observeTarefas()

    suspend fun sincronizarTarefas() = withContext(Dispatchers.IO) {
        val todos = api.getTodos()
        todos.forEach { dto ->
            val existente = dao.porRemoteId(dto.id)
            if (existente != null) {
                dao.atualizar(
                    existente.copy(
                        titulo = dto.title,
                        concluido = dto.completed
                    )
                )
            } else {
                dao.inserir(
                    Tarefa(
                        titulo = dto.title,
                        descricao = "",
                        concluido = dto.completed,
                        remoteId = dto.id
                    )
                )
            }
        }
    }

    suspend fun inserirTarefa(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        dao.inserir(tarefa)
    }
}
