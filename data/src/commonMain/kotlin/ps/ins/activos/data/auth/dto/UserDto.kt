package ps.ins.activos.data.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val correo: String,
    val contrasena: String
)
