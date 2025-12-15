package ps.ins.activos.domain.solicitud.usecase

import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.solicitud.repository.SolicitudRepository

class RejectSolicitudUseCase(
    private val repository: SolicitudRepository
) {
    suspend operator fun invoke(idSolicitud: String, idUsuarioAprobador: String, motivo: String): Result<Boolean, NetworkError> {
        return repository.rejectSolicitud(idSolicitud, idUsuarioAprobador, motivo)
    }
}
