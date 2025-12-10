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

interface RemoteActivosDataSource {
    suspend fun getActivosPropios(): Result<List<ActivoDto>, NetworkError>
}

class KtorRemoteActivosDataSource(
    private val httpClient: HttpClient,
    private val settings: Settings
) : RemoteActivosDataSource {
    override suspend fun getActivosPropios(): Result<List<ActivoDto>, NetworkError> {
        return safeCall<List<ActivoDto>> {
            // "activos_Propios" field wrapper needs to be handled.
            // Wait, the user said: "Structure of Response (JSON): Only interested in field 'activos_Propios' which is a list of assets."
            // This means the response is an object { "activos_Propios": [...] }
            // I need a wrapper DTO or handle it here if I cannot change DTO.
            // Let's assume I need a wrapper class for the response.
            
            val userId = settings.getString("userId", "")
            httpClient.get(constructUrl("/Activo")) {
                header("X-User-Id", userId)
            }.body<ActivosResponseDto>().activos_Propios
        }
    }
}

@kotlinx.serialization.Serializable
private data class ActivosResponseDto(
    val activos_Propios: List<ActivoDto>
)
