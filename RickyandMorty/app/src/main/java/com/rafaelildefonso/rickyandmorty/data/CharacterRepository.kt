package com.rafaelildefonso.rickyandmorty.data

import com.rafaelildefonso.rickyandmorty.data.local.CharacterDao
import com.rafaelildefonso.rickyandmorty.data.local.CharacterEntity
import com.rafaelildefonso.rickyandmorty.data.mapper.toEntity
import com.rafaelildefonso.rickyandmorty.data.remote.CharacterApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class CharacterRepository(
    private val apiService: CharacterApiService,
    private val characterDao: CharacterDao
) {

    val characters: Flow<List<CharacterEntity>> = characterDao.observeCharacters()

    suspend fun syncCharacters(): Result<Int> = try {
        val remoteCharacters = apiService.getCharacters().results
            .orEmpty()
            .map { it.toEntity() }
        characterDao.upsertAll(remoteCharacters)
        Result.success(remoteCharacters.size)
    } catch (error: Throwable) {
        Result.failure(error.toSyncError())
    }
}

private fun Throwable.toSyncError(): Throwable = when (this) {
    is IOException, is SocketTimeoutException -> SyncException("Sem conexão com a internet. Os dados salvos continuam disponíveis.", this)
    is HttpException -> SyncException("A Cidadela respondeu com o erro ${code()}. Tente novamente mais tarde.", this)
    else -> SyncException("Falha inesperada ao sincronizar: ${message ?: "erro desconhecido"}", this)
}

class SyncException(message: String, cause: Throwable) : Exception(message, cause)