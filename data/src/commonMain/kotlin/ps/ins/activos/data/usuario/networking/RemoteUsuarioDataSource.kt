package ps.ins.activos.data.usuario.networking

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import ps.ins.activos.data.core.networking.constructUrl
import ps.ins.activos.data.core.networking.safeCall
import ps.ins.activos.data.usuario.dto.UsuarioDto
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

interface RemoteUsuarioDataSource {
    suspend fun getUsuarioByFortia(claveFortia: String): Result<UsuarioDto, NetworkError>
}

class KtorRemoteUsuarioDataSource(
    private val httpClient: HttpClient,
    private val settings: Settings
) : RemoteUsuarioDataSource {
    override suspend fun getUsuarioByFortia(claveFortia: String): Result<UsuarioDto, NetworkError> {
        return safeCall<UsuarioDto> {
            val userId = settings.getString("userId", "")
            httpClient.get(constructUrl("/Usuario/claveFortia/$claveFortia")) {
                 header("X-User-Id", userId)
            }.body()
        }
    }
}
