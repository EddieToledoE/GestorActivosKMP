package ps.ins.activos.domain.activos.usecase

import ps.ins.activos.domain.activos.model.ActivoDetail
import ps.ins.activos.domain.activos.repository.ActivosRepository
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

class GetActivoDetailUseCase(
    private val repository: ActivosRepository
) {
    suspend operator fun invoke(id: String): Result<ActivoDetail, NetworkError> {
        return repository.getActivoDetail(id)
    }
}
