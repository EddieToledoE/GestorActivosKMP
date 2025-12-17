package ps.ins.activos.domain.solicitud.usecase

import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.solicitud.model.SolicitudConteo
import ps.ins.activos.domain.solicitud.repository.SolicitudRepository

class GetSolicitudConteoUseCase(
    private val repository: SolicitudRepository
) {
    suspend operator fun invoke(): Result<SolicitudConteo, NetworkError> {
        return repository.getConteo()
    }
}
