package com.rafaelildefonso.rickyandmorty.domain.model

import com.rafaelildefonso.rickyandmorty.data.local.CharacterEntity

data class CharacterUiState(
    val characters: List<CharacterEntity> = emptyList(),
    val syncState: SyncState = SyncState.Idle
)