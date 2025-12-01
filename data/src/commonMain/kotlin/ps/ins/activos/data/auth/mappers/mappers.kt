package ps.ins.activos.data.auth.mappers

import ps.ins.activos.data.auth.dto.CostCenterDto
import ps.ins.activos.data.auth.dto.LoginRequestDto
import ps.ins.activos.data.auth.dto.RoleDto
import ps.ins.activos.data.auth.dto.UserResponseDto
import ps.ins.activos.domain.auth.entities.CostCenter
import ps.ins.activos.domain.auth.entities.LoginUser
import ps.ins.activos.domain.auth.entities.Role
import ps.ins.activos.domain.auth.entities.User

fun LoginUser.toDto(): LoginRequestDto {
    return LoginRequestDto(
        correo = this.email,
        contrasena = this.password
    )
}

fun UserResponseDto.toDomain(): User {
    return User(
        id = this.idUsuario,
        fullName = "${this.nombres} ${this.apellidoPaterno}",
        roles = this.roles.map { it.toDomain() },
        permissions = this.permisos,
        costCenters = this.centrosCostoAcceso.map { it.toDomain() }
    )
}

fun RoleDto.toDomain(): Role {
    return Role(
        id = this.idRol,
        name = this.nombre
    )
}

fun CostCenterDto.toDomain(): CostCenter {
    return CostCenter(
        id = this.idCentroCosto,
        name = this.razonSocial,
        location = this.ubicacion,
        area = this.area
    )
}
