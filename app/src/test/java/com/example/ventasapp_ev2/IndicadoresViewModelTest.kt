package com.example.ventasapp_ev2

import com.example.ventasapp_ev2.data.model.IndicadoresResponse
import com.example.ventasapp_ev2.data.model.Indicador
import com.example.ventasapp_ev2.data.repository.IndicadoresRepository
import com.example.ventasapp_ev2.ui.indicadores.IndicadoresViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class IndicadoresViewModelTest : StringSpec({

    val dispatcher = StandardTestDispatcher()

    beforeSpec { Dispatchers.setMain(dispatcher) }
    afterSpec { Dispatchers.resetMain() }

    // Se mockea el repositorio: así se prueba solo la lógica del ViewModel, sin red

    "si la API responde, el estado muestra el dólar y la UF" {
        runTest(dispatcher) {
            val repositorio = mockk<IndicadoresRepository>()
            coEvery { repositorio.obtener() } returns IndicadoresResponse(
                uf = Indicador("uf", "Unidad de fomento", "2026-11-25T03:00:00.000Z", 39000.0),
                dolar = Indicador("dolar", "Dólar observado", "2026-11-25T03:00:00.000Z", 950.5)
            )

            val viewModel = IndicadoresViewModel(repositorio)
            advanceUntilIdle()

            viewModel.estado.value.cargando shouldBe false
            viewModel.estado.value.dolar shouldBe 950.5
            viewModel.estado.value.uf shouldBe 39000.0
            viewModel.estado.value.error shouldBe null
        }
    }

    "si falla la conexión, el estado muestra un mensaje de error" {
        runTest(dispatcher) {
            val repositorio = mockk<IndicadoresRepository>()
            coEvery { repositorio.obtener() } throws IOException("sin internet")

            val viewModel = IndicadoresViewModel(repositorio)
            advanceUntilIdle()

            viewModel.estado.value.cargando shouldBe false
            viewModel.estado.value.dolar shouldBe null
            viewModel.estado.value.error shouldBe "No se pudieron cargar los indicadores"
        }
    }
})
