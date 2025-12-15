package ps.ins.activos.domain.solicitud.usecase

import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.solicitud.model.SolicitudRequest
import ps.ins.activos.domain.solicitud.model.SolicitudResponse
import ps.ins.activos.domain.solicitud.repository.SolicitudRepository

class CreateTransferSolicitudUseCase(
    private val repository: SolicitudRepository
) {
    suspend operator fun invoke(request: SolicitudRequest): Result<SolicitudResponse, NetworkError> {
        return repository.createSolicitud(request)
    }
}
