package ps.ins.activos.presentation.core.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject
import ps.ins.activos.domain.permissions.PermissionManager

@Composable
fun WithPermission(
    permission: String,
    content: @Composable () -> Unit
) {
    val permissionManager = koinInject<PermissionManager>()
    val permissions by permissionManager.permissions.collectAsState()

    if (permissions.contains(permission)) {
        content()
    }
}

@Composable
fun WithAnyPermission(
    vararg permissions: String,
    content: @Composable () -> Unit
) {
    val permissionManager = koinInject<PermissionManager>()
    val userPermissions by permissionManager.permissions.collectAsState()

    if (permissions.any { userPermissions.contains(it) }) {
        content()
    }
}
