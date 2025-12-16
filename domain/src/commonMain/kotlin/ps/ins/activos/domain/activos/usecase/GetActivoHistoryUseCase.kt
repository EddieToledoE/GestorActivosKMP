package ps.ins.activos.domain.activos.usecase

import ps.ins.activos.domain.activos.model.ActivoHistory
import ps.ins.activos.domain.activos.repository.ActivosRepository
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

class GetActivoHistoryUseCase(
    private val repository: ActivosRepository
) {
    suspend operator fun invoke(id: String): Result<ActivoHistory, NetworkError> {
        return repository.getHistory(id)
    }
}
