package ps.ins.activos.data.solicitud.dto

import kotlinx.serialization.Serializable

@Serializable
data class SolicitudDetailDto(
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
