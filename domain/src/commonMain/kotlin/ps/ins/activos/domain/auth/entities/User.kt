package ps.ins.activos.domain.auth.entities

data class User(
    val id: String,
    val fullName: String,
    val roles: List<Role>,
    val permissions: List<String>,
    val costCenters: List<CostCenter>
)
