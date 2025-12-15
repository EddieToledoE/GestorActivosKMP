package ps.ins.activos.domain.usuario.repository

import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.usuario.model.Usuario

interface UsuarioRepository {
    suspend fun getUsuarioByFortia(claveFortia: String): Result<Usuario, NetworkError>
}
