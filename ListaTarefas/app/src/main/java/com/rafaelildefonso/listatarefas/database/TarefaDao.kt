package com.rafaelildefonso.listatarefas.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rafaelildefonso.listatarefas.model.Tarefa
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {

    @Query("SELECT * FROM tarefas ORDER BY id DESC")
    fun observeTarefas(): Flow<List<Tarefa>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserir(tarefa: Tarefa): Long

    @Update
    suspend fun atualizar(tarefa: Tarefa)

    @Query("SELECT * FROM tarefas WHERE remoteId = :remoteId LIMIT 1")
    suspend fun porRemoteId(remoteId: Int): Tarefa?
}
