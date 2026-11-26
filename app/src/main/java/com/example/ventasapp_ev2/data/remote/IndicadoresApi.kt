package com.example.ventasapp_ev2.data.remote

import com.example.ventasapp_ev2.data.model.IndicadoresResponse
import retrofit2.http.GET

// Define los endpoints HTTP de la API externa (igual que ApiService en la Guía 14)
interface IndicadoresApi {

    // GET https://mindicador.cl/api
    @GET("api")
    suspend fun obtener(): IndicadoresResponse
}
