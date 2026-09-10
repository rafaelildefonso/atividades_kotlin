package com.rafaelildefonso.atividadeapis.network

import com.rafaelildefonso.atividadeapis.model.CepResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {
    @GET("{cep}/json/")
    suspend fun buscarCep(@Path("cep") cep: String): CepResponse
}

object ViaCepClient {
    private const val BASE_URL = "https://viacep.com.br/ws/"

    val apiService: ViaCepService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViaCepService::class.java)
    }
}
