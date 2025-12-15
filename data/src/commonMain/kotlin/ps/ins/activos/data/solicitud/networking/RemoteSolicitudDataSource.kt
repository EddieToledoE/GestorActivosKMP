package ps.ins.activos.data.solicitud.networking

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ps.ins.activos.data.core.networking.constructUrl
import ps.ins.activos.data.core.networking.safeCall
import ps.ins.activos.data.solicitud.dto.AcceptSolicitudDto
import ps.ins.activos.data.solicitud.dto.RejectSolicitudDto
import ps.ins.activos.data.solicitud.dto.SolicitudDetailDto
import ps.ins.activos.data.solicitud.dto.SolicitudRequestDto
import ps.ins.activos.data.solicitud.dto.SolicitudResponseDto
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result


interface RemoteSolicitudDataSource {
    suspend fun createSolicitud(request: SolicitudRequestDto): Result<SolicitudResponseDto, NetworkError>
    suspend fun getSolicitudesEnviadas(userId: String): Result<List<SolicitudDetailDto>, NetworkError>
    suspend fun getSolicitudesRecibidas(userId: String): Result<List<SolicitudDetailDto>, NetworkError>
    suspend fun acceptSolicitud(idSolicitud: String, request: AcceptSolicitudDto): Result<Boolean, NetworkError>
    suspend fun rejectSolicitud(idSolicitud: String, request: RejectSolicitudDto): Result<Boolean, NetworkError>
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

    override suspend fun getSolicitudesEnviadas(userId: String): Result<List<SolicitudDetailDto>, NetworkError> {
        return safeCall<List<SolicitudDetailDto>> {
            val currentUserId = settings.getString("userId", "")
            // Use currentUserId for header, passed userId for path (should be same, but following interface)
            httpClient.get(constructUrl("/solicitud/pendientes/emisor/$userId")) {
                header("X-User-Id", currentUserId)
            }.body()
        }
    }

    override suspend fun getSolicitudesRecibidas(userId: String): Result<List<SolicitudDetailDto>, NetworkError> {
        return safeCall<List<SolicitudDetailDto>> {
            val currentUserId = settings.getString("userId", "")
            httpClient.get(constructUrl("/solicitud/pendientes/receptor/$userId")) {
                header("X-User-Id", currentUserId)
            }.body()
        }
    }

    override suspend fun acceptSolicitud(idSolicitud: String, request: AcceptSolicitudDto): Result<Boolean, NetworkError> {
        return safeCall<Boolean> {
            val currentUserId = settings.getString("userId", "")
            httpClient.put(constructUrl("/Solicitud/$idSolicitud/aceptar")) {
                header("X-User-Id", currentUserId)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            true // If call is successful, return true. Adjust if API returns specific response.
        }
    }

    override suspend fun rejectSolicitud(idSolicitud: String, request: RejectSolicitudDto): Result<Boolean, NetworkError> {
         return safeCall<Boolean> {
            val currentUserId = settings.getString("userId", "")
            httpClient.put(constructUrl("/Solicitud/$idSolicitud/rechazar")) {
                header("X-User-Id", currentUserId)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            true
        }
    }
}
