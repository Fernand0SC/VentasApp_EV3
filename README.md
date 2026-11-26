# VentasApp – Gestión móvil del registro y validación de ventas

Aplicación Android desarrollada para la asignatura **DSY1105 – Desarrollo de Aplicaciones Móviles** (Duoc UC), Evaluación Parcial 3.

## Integrantes

- Fernando Serrano
- Manuel Gaete

**Equipo:** Grupo 2

## Descripción

Producto Mínimo Viable que permite a los vendedores registrar ventas desde su teléfono, con su respaldo fotográfico, y consultarlas, modificarlas o eliminarlas mientras no estén validadas. Reemplaza el proceso manual de fotografías por mensajería y planillas por un registro centralizado y persistente.

**Todos los datos son ficticios**: no se usan clientes, comprobantes ni montos reales. La app no se conecta a sistemas comerciales reales; solo consulta una API pública de indicadores económicos (mindicador.cl).

## Funcionalidades

- Inicio de sesión con cuentas de prueba y menú principal.
- Navegación entre las pantallas Login → Menú → Lista de ventas → Formulario, con transiciones animadas.
- **CRUD completo de ventas** sobre Room:
  - **Crear:** formulario de nueva venta.
  - **Consultar:** lista de ventas del vendedor y apertura de cada venta con sus datos.
  - **Modificar:** edición de una venta existente.
  - **Eliminar:** ícono de papelera en la lista, con diálogo de confirmación.
- **Lista de ventas** almacenadas localmente, con código, tipo de servicio, monto y estado, actualizada automáticamente al registrar, modificar o eliminar.
- **Formulario de venta** con: tipo de convenio o servicio, fecha, cliente ficticio, monto, código de autorización y observaciones.
- **Validaciones** con error visible e ícono en cada campo: campos obligatorios, fecha válida y no futura, monto numérico mayor que 0 y código de autorización no duplicado. La lógica de validación está en el ViewModel, fuera de la interfaz.
- Estado inicial de la venta como **Borrador** o **Recibida**.
- **Persistencia local con Room**: las ventas se conservan al cerrar y reiniciar la aplicación.
- **Recursos nativos**: respaldo fotográfico ficticio mediante la **cámara** (con permiso en tiempo de ejecución) o la **galería**, con restricción de tipo (JPG, PNG, WebP) y tamaño máximo de 8 MB. La imagen pasa por el ViewModel, que la valida y guarda su ubicación en el estado; luego queda vinculada a la venta.
- **API externa con Retrofit**: el menú principal muestra la tarjeta "Indicadores del día" con el valor del dólar y de la UF, obtenidos desde mindicador.cl. Si no hay conexión, la app muestra un mensaje de error y sigue funcionando.
- Reglas de negocio: cada vendedor solo ve y modifica sus propias ventas, y una venta validada queda en solo lectura y no se puede eliminar.

### Funcionalidades adicionales

Adelantos del caso completo, que no forman parte de la calificación de esta evaluación:

- Perfil **encargado**: lista de ventas recibidas, detalle con respaldo fotográfico y acciones de validar, rechazar o solicitar corrección (con observación obligatoria).
- Historial de cambios por venta (acción, estados, usuario, fecha y observación).
- Visualización de la observación del encargado para el vendedor.
- Claves almacenadas como hash (SHA-256).

## Arquitectura

Patrón **MVVM** con estado inmutable (`UiState`) expuesto mediante `StateFlow`. Las pantallas solo muestran el estado y envían eventos; la validación y las reglas de negocio no viven en la interfaz. Los ViewModels acceden a los datos únicamente a través de los repositorios.

```
app/src/main/java/com/example/ventasapp_ev2/
├── data/
│   ├── local/        Entidades, DAOs y AppDatabase (Room)
│   ├── model/        Modelos de dominio (Perfil, EstadoVenta, Indicador)
│   ├── remote/       API externa (Retrofit): IndicadoresApi y RetrofitInstance
│   └── repository/   Repositorios (acceso a datos y reglas de negocio)
├── ui/
│   ├── components/   Componentes reutilizables (incluye IndicadoresCard)
│   ├── indicadores/  ViewModel de los indicadores (API externa)
│   ├── login/        Pantalla, estado y ViewModel de inicio de sesión
│   ├── menu/         Menú principal
│   ├── venta/        Lista y formulario de ventas, con su UiState y ViewModel
│   ├── revision/     Lista, detalle y revisión de ventas (encargado)
│   ├── navigation/   Rutas y NavHost
│   └── theme/        Tema Material 3
├── utils/            Manejo de archivos de respaldo (cámara y galería)
└── MainActivity.kt
```

## Tecnologías

- Kotlin
- Jetpack Compose y Material Design 3
- Arquitectura MVVM (ViewModel, StateFlow)
- Room (persistencia local)
- Navigation Compose
- Retrofit y Gson (consumo de API externa) y Corrutinas
- Recursos nativos: cámara y galería de imágenes
- Coil (visualización de imágenes)
- Pruebas: JUnit, Kotest y MockK

## API externa

La app consume la API pública **mindicador.cl** con **Retrofit** y la muestra en el menú principal.

| Método | Endpoint | Uso |
|--------|----------|-----|
| GET | `https://mindicador.cl/api` | Valores del día de la UF y el dólar |

Flujo de los datos, siguiendo MVVM:

```
IndicadoresApi (Retrofit) → IndicadoresRepository → IndicadoresViewModel → IndicadoresCard (en MenuScreen)
```

Archivos: `data/remote/IndicadoresApi.kt`, `data/remote/RetrofitInstance.kt`, `data/model/Indicador.kt`, `data/repository/IndicadoresRepository.kt`, `ui/indicadores/IndicadoresViewModel.kt` y `ui/components/IndicadoresCard.kt`.

## Pruebas

| Tipo | Clase | Ubicación |
|------|-------|-----------|
| View | `LoginScreenTest`, `MenuScreenTest` | `app/src/androidTest` (requieren emulador o celular) |
| ViewModel | `LoginViewModelTest`, `IndicadoresViewModelTest` | `app/src/test` |
| Repositorio | `RevisionRepositoryTest`, `IndicadoresRepositoryTest` | `app/src/test` |

Para ejecutarlas: clic derecho sobre la carpeta `test` o `androidTest` en Android Studio → *Run Tests*.

## Cómo ejecutar el proyecto

1. Clonar el repositorio:

```bash
git clone https://github.com/Fernand0SC/VentasApp_EV3.git
```

2. Abrir la carpeta en **Android Studio**.
3. Esperar a que termine la sincronización de Gradle (*Sync Project with Gradle Files*).
4. Ejecutar la app en un emulador o en un teléfono Android (mínimo Android 7.0, API 24). Se necesita conexión a internet para ver los indicadores.
5. Aceptar el permiso de cámara cuando la app lo solicite.

## Cuentas de prueba (ficticias)

| Perfil    | Usuario     | Contraseña |
|-----------|-------------|------------|
| Vendedor  | `vendedor`  | `1234`     |
| Encargado | `encargado` | `1234`     |

## Flujo de prueba sugerido

1. Entrar como **vendedor** y comprobar que en el menú aparece la tarjeta "Indicadores del día" con el dólar y la UF.
2. Crear una venta con respaldo fotográfico (cámara o galería).
3. Guardarla como borrador o enviarla, y comprobar que aparece en la lista.
4. Abrirla, modificar algún dato y guardar.
5. Eliminar una venta con la papelera y confirmar: desaparece de la lista.
6. Cerrar y reabrir la app: las ventas siguen en la lista.
7. (Adicional) Entrar como **encargado**, revisar una venta y consultar su historial.