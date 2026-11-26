package com.example.ventasapp_ev2.data.repository

import com.example.ventasapp_ev2.data.model.IndicadoresResponse
import com.example.ventasapp_ev2.data.remote.IndicadoresApi
import com.example.ventasapp_ev2.data.remote.RetrofitInstance

// Accede a los datos de la API externa usando Retrofit
class IndicadoresRepository(
    private val api: IndicadoresApi = RetrofitInstance.api
) {
    suspend fun obtener(): IndicadoresResponse = api.obtener()
}
