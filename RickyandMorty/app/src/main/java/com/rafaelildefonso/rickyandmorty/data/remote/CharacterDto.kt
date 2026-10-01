package com.rafaelildefonso.rickyandmorty.data.remote

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("image") val imageUrl: String?
)

data class CharacterPageDto(
    @SerializedName("info") val info: InfoDto?,
    @SerializedName("results") val results: List<CharacterDto>?
)

data class InfoDto(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)