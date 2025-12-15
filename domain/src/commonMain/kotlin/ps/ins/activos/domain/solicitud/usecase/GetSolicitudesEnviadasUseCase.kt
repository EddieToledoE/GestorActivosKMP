package ps.ins.activos.domain.solicitud.usecase

import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.solicitud.model.SolicitudDetail
import ps.ins.activos.domain.solicitud.repository.SolicitudRepository

class GetSolicitudesEnviadasUseCase(
    private val repository: SolicitudRepository
) {
    suspend operator fun invoke(userId: String): Result<List<SolicitudDetail>, NetworkError> {
        return repository.getSolicitudesEnviadas(userId)
    }
}
