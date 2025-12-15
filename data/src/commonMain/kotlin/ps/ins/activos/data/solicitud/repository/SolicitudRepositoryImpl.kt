package ps.ins.activos.data.solicitud.repository

import ps.ins.activos.data.solicitud.dto.SolicitudRequestDto
import ps.ins.activos.data.solicitud.dto.SolicitudResponseDto
import ps.ins.activos.data.solicitud.networking.RemoteSolicitudDataSource
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.core.util.map
import ps.ins.activos.domain.solicitud.model.SolicitudRequest
import ps.ins.activos.domain.solicitud.model.SolicitudResponse
import ps.ins.activos.domain.solicitud.repository.SolicitudRepository

class SolicitudRepositoryImpl(
    private val remoteDataSource: RemoteSolicitudDataSource
) : SolicitudRepository {
    override suspend fun createSolicitud(request: SolicitudRequest): Result<SolicitudResponse, NetworkError> {
        val requestDto = SolicitudRequestDto(
            idEmisor = request.idEmisor,
            idReceptor = request.idReceptor,
            idActivo = request.idActivo,
            tipo = request.tipo,
            mensaje = request.mensaje
        )
        return remoteDataSource.createSolicitud(requestDto).map { dto ->
            SolicitudResponse(
                message = dto.message,
                id = dto.id
            )
        }
    }
}
