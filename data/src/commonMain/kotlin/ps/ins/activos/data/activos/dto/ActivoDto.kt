package ps.ins.activos.data.activos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActivoDto(
    val idActivo: String,
    val nombre: String,
    val categoria: String,
    val responsable: String,
    val centroCosto: String,
    val estado: String,
    val fechaAdquisicion: String?,
    val imagenUrl: String,
    val marca: String,
    val modelo: String,
    val etiqueta: String
)
