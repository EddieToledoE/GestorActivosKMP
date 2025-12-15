package ps.ins.activos.domain.usuario.model

data class Usuario(
    val idUsuario: String,
    val nombreCompleto: String,
    val claveFortia: String,
    val idCentroCosto: Int,
    val ubicacion: String,
    val razonSocial: String,
    val area: String
)
