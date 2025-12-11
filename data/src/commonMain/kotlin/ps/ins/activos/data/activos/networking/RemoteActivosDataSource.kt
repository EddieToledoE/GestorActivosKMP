package ps.ins.activos.data.activos.networking

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import ps.ins.activos.data.activos.dto.ActivoDto
import ps.ins.activos.data.core.networking.constructUrl
import ps.ins.activos.data.core.networking.safeCall
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

import ps.ins.activos.data.activos.dto.ActivosResponseDto

interface RemoteActivosDataSource {
    suspend fun getActivosPropios(): Result<List<ActivoDto>, NetworkError>
    suspend fun getAllActivos(): Result<ActivosResponseDto, NetworkError>
}

class KtorRemoteActivosDataSource(
    private val httpClient: HttpClient,
    private val settings: Settings
) : RemoteActivosDataSource {
    override suspend fun getActivosPropios(): Result<List<ActivoDto>, NetworkError> {
        return safeCall<List<ActivoDto>> {
            val userId = settings.getString("userId", "")
            httpClient.get(constructUrl("/Activo")) {
                header("X-User-Id", userId)
            }.body<ActivosResponseDto>().activos_Propios
        }
    }

    override suspend fun getAllActivos(): Result<ActivosResponseDto, NetworkError> {
        return safeCall<ActivosResponseDto> {
            val userId = settings.getString("userId", "")
            httpClient.get(constructUrl("/Activo")) {
                header("X-User-Id", userId)
            }.body<ActivosResponseDto>()
        }
    }
}

