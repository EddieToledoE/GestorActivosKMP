package ps.ins.activos

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.russhwolf.settings.Settings
import ps.ins.activos.presentation.auth.login.LoginScreen
import ps.ins.activos.presentation.home.HomeScreen
import ps.ins.activos.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme(dynamicColor = false) {
        val settings = Settings()
        val isLoggedIn = settings.getBoolean("isLoggedIn", false)
        val initialScreen = if (isLoggedIn) HomeScreen else LoginScreen
        Navigator(initialScreen)
    }
}

