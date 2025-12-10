package ps.ins.activos.domain.activos.model

data class ActivoEntity(
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
