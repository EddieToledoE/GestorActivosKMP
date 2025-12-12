package ps.ins.activos.domain.activos.model

data class ActivoDetail(
    val idActivo: String,
    val descripcion: String,
    val numeroSerie: String,
    val donacion: Boolean,
    val facturaPDF: String?,
    val facturaXML: String?,
    val cuentaContable: String?,
    val valorAdquisicion: Double?,
    val fechaAdquisicion: String?,
    val fechaAlta: String,
    val portaEtiqueta: Boolean,
    val cuentaContableEtiqueta: Boolean
)
