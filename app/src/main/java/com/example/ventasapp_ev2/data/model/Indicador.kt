package com.example.ventasapp_ev2.data.model

// Un indicador económico entregado por la API externa (mindicador.cl)
data class Indicador(
    val codigo: String,
    val nombre: String,
    val fecha: String,
    val valor: Double
)

// Respuesta de https://mindicador.cl/api
// Solo se declaran los campos que usa la app; Gson ignora el resto del JSON
data class IndicadoresResponse(
    val uf: Indicador?,
    val dolar: Indicador?
)
