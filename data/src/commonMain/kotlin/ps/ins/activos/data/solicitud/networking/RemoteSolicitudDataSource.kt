package ps.ins.activos.data.solicitud.networking

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ps.ins.activos.data.core.networking.constructUrl
import ps.ins.activos.data.core.networking.safeCall
import ps.ins.activos.data.solicitud.dto.SolicitudRequestDto
import ps.ins.activos.data.solicitud.dto.SolicitudResponseDto
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

interface RemoteSolicitudDataSource {
    suspend fun createSolicitud(request: SolicitudRequestDto): Result<SolicitudResponseDto, NetworkError>
}

class KtorRemoteSolicitudDataSource(
    private val httpClient: HttpClient,
    private val settings: Settings
) : RemoteSolicitudDataSource {
    override suspend fun createSolicitud(request: SolicitudRequestDto): Result<SolicitudResponseDto, NetworkError> {
        return safeCall<SolicitudResponseDto> {
            val userId = settings.getString("userId", "")
            httpClient.post(constructUrl("/solicitud")) {
                 header("X-User-Id", userId)
                 contentType(ContentType.Application.Json)
                 setBody(request)
            }.body()
        }
    }
}
