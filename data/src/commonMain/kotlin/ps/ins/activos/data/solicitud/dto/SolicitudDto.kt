package ps.ins.activos.data.solicitud.dto

import kotlinx.serialization.Serializable

@Serializable
data class SolicitudRequestDto(
    val idEmisor: String,
    val idReceptor: String,
    val idActivo: String,
    val tipo: String,
    val mensaje: String
)

@Serializable
data class SolicitudResponseDto(
    val message: String,
    val id: String
)
