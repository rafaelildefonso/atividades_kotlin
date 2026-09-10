package com.rafaelildefonso.pokedex.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites
)

data class PokemonSprites(
    @SerializedName("front_default") val frontDefault: String?,
    val other: PokemonOtherSprites?
)

data class PokemonOtherSprites(
    val showdown: PokemonShowdownSprites?
)

data class PokemonShowdownSprites(
    @SerializedName("front_default") val frontDefault: String?
)