package com.example.ventasapp_ev2

import com.example.ventasapp_ev2.data.model.IndicadoresResponse
import com.example.ventasapp_ev2.data.model.Indicador
import com.example.ventasapp_ev2.data.remote.IndicadoresApi
import com.example.ventasapp_ev2.data.repository.IndicadoresRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class IndicadoresRepositoryTest : StringSpec({

    // Se mockea la API (la red); se prueba que el repositorio entregue lo que responde
    "obtener devuelve los indicadores que entrega la API" {
        runTest {
            val respuesta = IndicadoresResponse(
                uf = Indicador("uf", "Unidad de fomento", "2026-11-25T03:00:00.000Z", 39000.0),
                dolar = Indicador("dolar", "Dólar observado", "2026-11-25T03:00:00.000Z", 950.5)
            )
            val api = mockk<IndicadoresApi>()
            coEvery { api.obtener() } returns respuesta

            val resultado = IndicadoresRepository(api).obtener()

            resultado shouldBe respuesta
            coVerify(exactly = 1) { api.obtener() }
        }
    }
})
