package ps.ins.activos.data.core.networking

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

suspend fun <T : Any> safeCall(
    execute: suspend () -> T
): Result<T, NetworkError> {
    return try {
        Result.Success(execute())
    } catch (e: ClientRequestException) {
        println("Error de cliente (4xx): ${e.response.status}")
        e.printStackTrace()
        Result.Error(NetworkError.BAD_REQUEST)
    } catch (e: ServerResponseException) {
        println("Error de servidor (5xx): ${e.response.status}")
        e.printStackTrace()
        Result.Error(NetworkError.INTERNAL_SERVER_ERROR)
    } catch (e: IOException) {
        println("Error de Red/Conexión (IO): No se pudo alcanzar el servidor. ¿Firewall? ¿IP incorrecta?")
        e.printStackTrace()
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        println("Error de serialización: ${e.message}")
        e.printStackTrace()
        Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        // Este bloque ahora nos dará la causa exacta del error UNKNOWN
        println("Error desconocido: ${e::class.simpleName} - ${e.message}")
        e.printStackTrace()
        Result.Error(NetworkError.UNKNOWN)
    }
}
