package com.example.ventasapp_ev2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ventasapp_ev2.ui.indicadores.IndicadoresUiState
import java.util.Locale

// Tarjeta que muestra los datos de la API externa (dólar y UF)
@Composable
fun IndicadoresCard(estado: IndicadoresUiState, modifier: Modifier = Modifier) {
    val formato = Locale.forLanguageTag("es-CL")
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Indicadores del día", style = MaterialTheme.typography.titleSmall)
            when {
                estado.cargando -> Text("Cargando…")
                estado.error != null -> Text(estado.error)
                else -> {
                    estado.dolar?.let { Text("Dólar observado: $" + String.format(formato, "%,.2f", it)) }
                    estado.uf?.let { Text("UF: $" + String.format(formato, "%,.2f", it)) }
                    estado.fecha?.let { Text("Fecha: $it", style = MaterialTheme.typography.bodySmall) }
                }
            }
        }
    }
}
