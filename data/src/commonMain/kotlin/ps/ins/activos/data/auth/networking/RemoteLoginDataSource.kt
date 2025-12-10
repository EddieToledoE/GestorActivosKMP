package ps.ins.activos.data.auth.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ps.ins.activos.data.auth.dto.UserResponseDto
import ps.ins.activos.data.auth.mappers.toDomain
import ps.ins.activos.data.auth.mappers.toDto
import ps.ins.activos.data.core.networking.constructUrl
import ps.ins.activos.data.core.networking.safeCall
import ps.ins.activos.domain.auth.datasource.LoginDataSource
import ps.ins.activos.domain.auth.entities.LoginUser
import ps.ins.activos.domain.auth.entities.User
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.core.util.map


class RemoteLoginDataSource(private val httpClient: HttpClient) : LoginDataSource {
    override suspend fun doLogin(user: LoginUser): Result<User, NetworkError> {
        return safeCall<String> {
            println("🚀 Iniciando petición POST a /Auth/login")
            val response = httpClient.post(
                urlString = constructUrl("/Auth/login")) {
                setBody(user.toDto())
            }
            println("✅ Petición POST finalizada. Status: ${response.status}")
            
            val body = response.bodyAsText()
            println("📦 Login raw response:\n$body")
            body // devuelve como String
        }.map { raw ->
            val dto = Json.decodeFromString<UserResponseDto>(raw)
            dto.toDomain()
        }
    }

}
