package ps.ins.activos.domain.solicitud.repository

import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.solicitud.model.SolicitudRequest
import ps.ins.activos.domain.solicitud.model.SolicitudResponse

interface SolicitudRepository {
    suspend fun createSolicitud(request: SolicitudRequest): Result<SolicitudResponse, NetworkError>
}
