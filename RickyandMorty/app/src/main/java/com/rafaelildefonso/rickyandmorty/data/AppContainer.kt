package com.rafaelildefonso.rickyandmorty.data

import android.content.Context
import com.rafaelildefonso.rickyandmorty.data.local.AppDatabase
import com.rafaelildefonso.rickyandmorty.data.remote.CharacterApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val database: AppDatabase = AppDatabase.getInstance(context)

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: CharacterApiService = retrofit.create(CharacterApiService::class.java)

    val characterRepository: CharacterRepository by lazy {
        CharacterRepository(apiService = apiService, characterDao = database.characterDao())
    }

    private companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}