package ps.ins.activos.data.usuario.mapper

import ps.ins.activos.data.usuario.dto.UsuarioDto
import ps.ins.activos.domain.usuario.model.Usuario

fun UsuarioDto.toDomain(): Usuario {
    return Usuario(
        idUsuario = idUsuario,
        nombreCompleto = nombreCompleto,
        claveFortia = claveFortia,
        idCentroCosto = idCentroCosto,
        ubicacion = ubicacion,
        razonSocial = razonSocial,
        area = area
    )
}
