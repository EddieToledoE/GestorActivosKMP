package ps.ins.activos.domain.activos.usecase

import ps.ins.activos.domain.activos.model.ActivoEntity
import ps.ins.activos.domain.activos.repository.ActivosRepository
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

class GetActivosPropiosUseCase(
    private val repository: ActivosRepository
) {
    suspend operator fun invoke(): Result<List<ActivoEntity>, NetworkError> {
        return repository.getActivosPropios()
    }
}
