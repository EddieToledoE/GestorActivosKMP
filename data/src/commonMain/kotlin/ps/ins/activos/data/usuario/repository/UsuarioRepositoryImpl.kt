package ps.ins.activos.data.usuario.repository

import ps.ins.activos.data.usuario.mapper.toDomain
import ps.ins.activos.data.usuario.networking.RemoteUsuarioDataSource
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.core.util.map
import ps.ins.activos.domain.usuario.model.Usuario
import ps.ins.activos.domain.usuario.repository.UsuarioRepository

class UsuarioRepositoryImpl(
    private val remoteDataSource: RemoteUsuarioDataSource
) : UsuarioRepository {
    override suspend fun getUsuarioByFortia(claveFortia: String): Result<Usuario, NetworkError> {
        return remoteDataSource.getUsuarioByFortia(claveFortia).map { it.toDomain() }
    }
}
