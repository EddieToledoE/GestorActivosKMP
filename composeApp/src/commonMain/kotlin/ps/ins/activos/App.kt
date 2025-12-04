package ps.ins.activos

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ps.ins.activos.screens.LoginRoot
import ps.ins.activos.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme(dynamicColor = false) {
        Navigator(LoginRoot)
    }
}

