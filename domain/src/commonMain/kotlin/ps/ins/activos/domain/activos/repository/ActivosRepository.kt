package ps.ins.activos.domain.activos.repository

import ps.ins.activos.domain.activos.model.ActivoEntity
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

interface ActivosRepository {
    suspend fun getActivosPropios(): Result<List<ActivoEntity>, NetworkError>
    suspend fun getAllActivos(): Result<List<ActivoEntity>, NetworkError>
    suspend fun getActivoDetail(id: String): Result<ps.ins.activos.domain.activos.model.ActivoDetail, NetworkError>
}
