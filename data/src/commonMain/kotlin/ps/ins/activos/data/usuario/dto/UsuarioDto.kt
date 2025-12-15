package ps.ins.activos.data.usuario.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDto(
    val idUsuario: String,
    val nombreCompleto: String,
    val claveFortia: String,
    val idCentroCosto: Int,
    val ubicacion: String,
    val razonSocial: String,
    val area: String
)
