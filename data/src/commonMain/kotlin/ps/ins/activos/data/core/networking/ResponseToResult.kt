package ps.ins.activos.data.core.networking

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

suspend inline fun <reified T> responseToResult(
    response: HttpResponse,
) : Result<T, NetworkError>{
    return when(response.status.value){
        in 200..299 -> {
            try {
                Result.Success(data = response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(error = NetworkError.SERIALIZATION)
            }
        }
        401 -> Result.Error(error = NetworkError.INCORRECT_CREDENTIALS)
        408 -> Result.Error(error = NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(error = NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(error = NetworkError.SERVER_ERROR)
        else -> Result.Error(error = NetworkError.UNKNOWN)
    }
}