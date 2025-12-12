package ps.ins.activos.domain.permissions

import kotlinx.coroutines.flow.StateFlow

interface PermissionManager {
    val permissions: StateFlow<List<String>>

    fun hasPermission(permission: String): Boolean
    fun hasAny(vararg permissions: String): Boolean
    suspend fun savePermissions(permissions: List<String>)
    suspend fun clearPermissions()
}
