package ps.ins.activos.domain.solicitud.model

data class SolicitudDetail(
    val idSolicitud: String,
    val idEmisor: String,
    val nombreEmisor: String,
    val idReceptor: String,
    val nombreReceptor: String,
    val idActivo: String,
    val etiquetaActivo: String,
    val descripcionActivo: String,
    val tipo: String,
    val mensaje: String,
    val fecha: String,
    val estado: String
)
