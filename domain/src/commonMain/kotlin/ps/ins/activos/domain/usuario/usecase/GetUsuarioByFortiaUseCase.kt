package ps.ins.activos.domain.usuario.usecase

import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.usuario.model.Usuario
import ps.ins.activos.domain.usuario.repository.UsuarioRepository

class GetUsuarioByFortiaUseCase(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke(claveFortia: String): Result<Usuario, NetworkError> {
        return repository.getUsuarioByFortia(claveFortia)
    }
}
