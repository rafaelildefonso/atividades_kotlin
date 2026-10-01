package com.rafaelildefonso.rickyandmorty.data.mapper

import com.rafaelildefonso.rickyandmorty.data.local.CharacterEntity
import com.rafaelildefonso.rickyandmorty.data.remote.CharacterDto

private const val UNKNOWN_VALUE = "Desconhecido"

fun CharacterDto.toEntity(): CharacterEntity = CharacterEntity(
    id = id,
    name = name?.trim()?.takeIf { it.isNotEmpty() } ?: UNKNOWN_VALUE,
    species = species?.trim()?.takeIf { it.isNotEmpty() } ?: UNKNOWN_VALUE,
    status = status?.trim()?.takeIf { it.isNotEmpty() } ?: UNKNOWN_VALUE,
    imageUrl = imageUrl?.trim().orEmpty()
)