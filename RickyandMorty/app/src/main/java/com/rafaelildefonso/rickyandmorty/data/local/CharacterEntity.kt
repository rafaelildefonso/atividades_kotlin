package com.rafaelildefonso.rickyandmorty.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)