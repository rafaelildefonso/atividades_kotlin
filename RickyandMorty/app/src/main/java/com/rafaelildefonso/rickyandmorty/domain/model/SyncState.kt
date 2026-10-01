package com.rafaelildefonso.rickyandmorty.domain.model

sealed interface SyncState {
    data object Idle : SyncState
    data object Loading : SyncState
    data class Success(val totalCharacters: Int) : SyncState
    data class Error(val message: String) : SyncState
}