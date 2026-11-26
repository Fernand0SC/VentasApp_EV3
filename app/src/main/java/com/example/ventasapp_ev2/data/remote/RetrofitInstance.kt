package com.example.ventasapp_ev2.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton con la configuración de Retrofit (igual que en la Guía 14)
object RetrofitInstance {

    val api: IndicadoresApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://mindicador.cl/")                  // URL base de la API
            .addConverterFactory(GsonConverterFactory.create()) // convierte JSON a data class
            .build()
            .create(IndicadoresApi::class.java)
    }
}
