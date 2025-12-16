package ps.ins.activos.data.activos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActivoHistoryDto(
    val idActivo: String,
    val nombre: String,
    val marca: String,
    val modelo: String,
    val etiqueta: String,
    val numeroSerie: String,
    val categoria: String,
    val estado: String,
    val fechaAdquisicion: String? = null,
    val imagenUrl: String,
    val responsableActualId: String? = null,
    val responsableActual: String? = null,
    val centroCostoActual: String? = null,
    val historialReubicaciones: List<ReubicacionDto> = emptyList()
)

@Serializable
data class ReubicacionDto(
    val idReubicacion: Int,
    val fecha: String,
    val motivo: String,
    val idUsuarioAnterior: String? = null,
    val usuarioAnterior: String? = null,
    val centroCostoAnterior: String? = null,
    val idUsuarioNuevo: String? = null,
    val usuarioNuevo: String? = null,
    val centroCostoNuevo: String? = null
)
