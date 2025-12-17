package ps.ins.activos.data.solicitud.repository

import ps.ins.activos.data.solicitud.dto.AcceptSolicitudDto
import ps.ins.activos.data.solicitud.dto.RejectSolicitudDto
import ps.ins.activos.data.solicitud.dto.SolicitudRequestDto
import ps.ins.activos.data.solicitud.dto.SolicitudResponseDto
import ps.ins.activos.data.solicitud.networking.RemoteSolicitudDataSource
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.core.util.map
import ps.ins.activos.domain.solicitud.model.SolicitudRequest
import ps.ins.activos.domain.solicitud.model.SolicitudResponse
import ps.ins.activos.domain.solicitud.repository.SolicitudRepository

import ps.ins.activos.data.solicitud.dto.SolicitudDetailDto
import ps.ins.activos.domain.solicitud.model.SolicitudConteo
import ps.ins.activos.domain.solicitud.model.SolicitudDetail

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

    override suspend fun getSolicitudesEnviadas(userId: String): Result<List<SolicitudDetail>, NetworkError> {
        return remoteDataSource.getSolicitudesEnviadas(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getSolicitudesRecibidas(userId: String): Result<List<SolicitudDetail>, NetworkError> {
        return remoteDataSource.getSolicitudesRecibidas(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun acceptSolicitud(idSolicitud: String, idUsuarioAprobador: String): Result<Boolean, NetworkError> {
        val dto = AcceptSolicitudDto(idUsuarioAprobador)
        return remoteDataSource.acceptSolicitud(idSolicitud, dto)
    }

    override suspend fun rejectSolicitud(idSolicitud: String, idUsuarioAprobador: String, motivoRechazo: String): Result<Boolean, NetworkError> {
        val dto = RejectSolicitudDto(idUsuarioAprobador, motivoRechazo)
        return remoteDataSource.rejectSolicitud(idSolicitud, dto)
    }

    override suspend fun getConteo(): Result<SolicitudConteo, NetworkError> {
        return remoteDataSource.getConteo().map { dto ->
            SolicitudConteo(
                total = dto.total,
                entrantes = dto.individual.entrantes,
                salientes = dto.individual.salientes
            )
        }
    }

    private fun SolicitudDetailDto.toDomain(): SolicitudDetail {
        return SolicitudDetail(
            idSolicitud = idSolicitud,
            idEmisor = idEmisor,
            nombreEmisor = nombreEmisor,
            idReceptor = idReceptor,
            nombreReceptor = nombreReceptor,
            idActivo = idActivo,
            etiquetaActivo = etiquetaActivo,
            descripcionActivo = descripcionActivo,
            tipo = tipo,
            mensaje = mensaje,
            fecha = fecha,
            estado = estado
        )
    }
}
