package ps.ins.activos.data.permissions

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ps.ins.activos.domain.permissions.PermissionManager

class PermissionManagerImpl(
    private val settings: Settings
) : PermissionManager {

    private val _permissions = MutableStateFlow<List<String>>(emptyList())
    override val permissions: StateFlow<List<String>> = _permissions.asStateFlow()

    init {
        // Load permissions from settings on initialization
        loadPermissions()
    }

    private fun loadPermissions() {
        val permissionsString = settings.getString(KEY_PERMISSIONS, "")
        if (permissionsString.isNotEmpty()) {
            _permissions.value = permissionsString.split(SEPARATOR)
        } else {
            _permissions.value = emptyList()
        }
    }

    override fun hasPermission(permission: String): Boolean {
        return _permissions.value.contains(permission)
    }

    override fun hasAny(vararg permissions: String): Boolean {
        return permissions.any { _permissions.value.contains(it) }
    }

    override suspend fun savePermissions(permissions: List<String>) {
        _permissions.value = permissions
        val permissionsString = permissions.joinToString(SEPARATOR)
        settings[KEY_PERMISSIONS] = permissionsString
    }

    override suspend fun clearPermissions() {
        _permissions.value = emptyList()
        settings.remove(KEY_PERMISSIONS)
    }

    companion object {
        private const val KEY_PERMISSIONS = "user_permissions"
        private const val SEPARATOR = "|"
    }
}
