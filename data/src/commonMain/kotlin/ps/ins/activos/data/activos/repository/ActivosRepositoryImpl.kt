package ps.ins.activos.data.activos.repository

import ps.ins.activos.data.activos.mapper.toDomain
import ps.ins.activos.data.activos.networking.RemoteActivosDataSource
import ps.ins.activos.domain.activos.model.ActivoEntity
import ps.ins.activos.domain.activos.repository.ActivosRepository
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.core.util.map

class ActivosRepositoryImpl(
    private val remoteDataSource: RemoteActivosDataSource
) : ActivosRepository {
    override suspend fun getActivosPropios(): Result<List<ActivoEntity>, NetworkError> {
        return remoteDataSource.getActivosPropios().map { dtos ->
            dtos.map { it.toDomain() }
        }
    }
}
