package ps.ins.activos.data.activos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActivosResponseDto(
    val activos_Propios: List<ActivoDto>,
    val centrosCosto: Map<String, List<ActivoDto>>? = null
)
