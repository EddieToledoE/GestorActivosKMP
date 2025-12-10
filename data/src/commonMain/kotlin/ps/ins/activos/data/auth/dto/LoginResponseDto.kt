package ps.ins.activos.data.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val idUsuario: String,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val correo: String,
    val claveFortia: String?,
    val idCentroCostoPrincipal: Int,
    val centroCostoPrincipal: String,
    val roles: List<RoleDto>,
    val permisos: List<String>,
    val centrosCostoAcceso: List<CostCenterDto>
)

@Serializable
data class RoleDto(
    val idRol: Int,
    val nombre: String,
    val permisos: List<String>
)

@Serializable
data class CostCenterDto(
    val idCentroCosto: Int,
    val razonSocial: String,
    val ubicacion: String,
    val area: String,
    val activo: Boolean
)
