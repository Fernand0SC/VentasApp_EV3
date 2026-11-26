package com.example.ventasapp_ev2.ui.indicadores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ventasapp_ev2.data.repository.IndicadoresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class IndicadoresUiState(
    val cargando: Boolean = true,
    val dolar: Double? = null,
    val uf: Double? = null,
    val fecha: String? = null,
    val error: String? = null
)

// Mantiene el estado de los datos obtenidos de la API externa
class IndicadoresViewModel(
    private val repository: IndicadoresRepository = IndicadoresRepository()
) : ViewModel() {

    private val _estado = MutableStateFlow(IndicadoresUiState())
    val estado: StateFlow<IndicadoresUiState> = _estado.asStateFlow()

    // Se llama automáticamente al iniciar
    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _estado.value = IndicadoresUiState(cargando = true)
            _estado.value = try {
                val r = repository.obtener()
                IndicadoresUiState(
                    cargando = false,
                    dolar = r.dolar?.valor,
                    uf = r.uf?.valor,
                    fecha = r.dolar?.fecha?.take(10)
                )
            } catch (e: Exception) {
                IndicadoresUiState(cargando = false, error = "No se pudieron cargar los indicadores")
            }
        }
    }
}
