package ps.ins.activos.domain.solicitud.repository

import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.solicitud.model.SolicitudConteo
import ps.ins.activos.domain.solicitud.model.SolicitudRequest
import ps.ins.activos.domain.solicitud.model.SolicitudResponse

import ps.ins.activos.domain.solicitud.model.SolicitudDetail

interface SolicitudRepository {
    suspend fun createSolicitud(request: SolicitudRequest): Result<SolicitudResponse, NetworkError>
    suspend fun getSolicitudesEnviadas(userId: String): Result<List<SolicitudDetail>, NetworkError>
    suspend fun getSolicitudesRecibidas(userId: String): Result<List<SolicitudDetail>, NetworkError>
    suspend fun acceptSolicitud(idSolicitud: String, idUsuarioAprobador: String): Result<Boolean, NetworkError>
    suspend fun rejectSolicitud(idSolicitud: String, idUsuarioAprobador: String, motivoRechazo: String): Result<Boolean, NetworkError>
    suspend fun getConteo(): Result<SolicitudConteo, NetworkError>
}
