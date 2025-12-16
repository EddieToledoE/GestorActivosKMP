package ps.ins.activos.domain.activos.model

data class ActivoHistory(
    val idActivo: String,
    val nombre: String,
    val marca: String,
    val modelo: String,
    val etiqueta: String,
    val numeroSerie: String,
    val categoria: String,
    val estado: String,
    val fechaAdquisicion: String,
    val imagenUrl: String,
    val responsableActual: String,
    val centroCostoActual: String,
    val historial: List<Reubicacion>
)

data class Reubicacion(
    val idReubicacion: Int,
    val fecha: String,
    val motivo: String,
    val usuarioAnterior: String,
    val centroCostoAnterior: String,
    val usuarioNuevo: String,
    val centroCostoNuevo: String
)
