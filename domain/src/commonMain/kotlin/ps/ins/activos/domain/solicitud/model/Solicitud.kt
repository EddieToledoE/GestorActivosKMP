package ps.ins.activos.domain.solicitud.model

data class SolicitudRequest(
    val idEmisor: String,
    val idReceptor: String,
    val idActivo: String,
    val tipo: String = "transferencia de activos entre usuarios",
    val mensaje: String
)

data class SolicitudResponse(
    val message: String,
    val id: String
)
