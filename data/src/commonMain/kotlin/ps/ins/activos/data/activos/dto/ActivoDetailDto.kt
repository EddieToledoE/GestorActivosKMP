package ps.ins.activos.data.activos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActivoDetailDto(
    val idActivo: String,
    val imagenUrl: String,
    val responsableId: String,
    val idCategoria: Int,
    val marca: String,
    val modelo: String,
    val descripcion: String,
    val etiqueta: String,
    val numeroSerie: String,
    val donacion: Boolean,
    val facturaPDF: String?,
    val facturaXML: String?,
    val cuentaContable: String?,
    val valorAdquisicion: Double?,
    val estatus: String,
    val fechaAdquisicion: String?,
    val fechaAlta: String,
    val portaEtiqueta: Boolean,
    val cuentaContableEtiqueta: Boolean
)
