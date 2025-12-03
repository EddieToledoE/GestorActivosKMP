package ps.ins.activos.data.auth.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ps.ins.activos.data.auth.dto.UserResponseDto
import ps.ins.activos.data.auth.mappers.toDomain
import ps.ins.activos.data.auth.mappers.toDto
import ps.ins.activos.data.core.networking.safeCall
import ps.ins.activos.domain.auth.datasource.LoginDataSource
import ps.ins.activos.domain.auth.entities.LoginUser
import ps.ins.activos.domain.auth.entities.User
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.core.util.map

class RemoteLoginDataSource(private val httpClient: HttpClient) : LoginDataSource {
    override suspend fun doLogin(user: LoginUser): Result<User, NetworkError> {
        return safeCall<UserResponseDto> {
            httpClient.post("http://192.168.110.62:45455/api/Auth/login") {
                setBody(user.toDto())
            }.body<UserResponseDto>()
        }.map { userResponseDto ->
            userResponseDto.toDomain()
        }
    }
}
