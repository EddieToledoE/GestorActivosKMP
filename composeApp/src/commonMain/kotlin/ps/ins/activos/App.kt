package ps.ins.activos

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ps.ins.activos.screens.LoginRoot
import ps.ins.activos.ui.theme.AppTheme

import com.russhwolf.settings.Settings
import ps.ins.activos.screens.HomeRoot

@Composable
fun App() {
    AppTheme(dynamicColor = true) {
        val settings = Settings()
        val isLoggedIn = settings.getBoolean("isLoggedIn", false)
        val initialScreen = if (isLoggedIn) HomeRoot else LoginRoot
        
        Navigator(initialScreen)
    }
}

