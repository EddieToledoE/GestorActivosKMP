package ps.ins.activos.data.solicitud.dto

import kotlinx.serialization.Serializable

@Serializable
data class SolicitudConteoDto(
    val total: Int,
    val individual: IndividualConteoDto
)

@Serializable
data class IndividualConteoDto(
    val entrantes: Int,
    val salientes: Int
)
