package ps.ins.activos.data.solicitud.dto

import kotlinx.serialization.Serializable

@Serializable
data class AcceptSolicitudDto(
    val idUsuarioAprobador: String
)

@Serializable
data class RejectSolicitudDto(
    val idUsuarioAprobador: String,
    val motivoRechazo: String
)
