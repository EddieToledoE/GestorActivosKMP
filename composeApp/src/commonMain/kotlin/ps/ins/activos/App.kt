package ps.ins.activos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import com.russhwolf.settings.Settings
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import ps.ins.activos.presentation.auth.login.LoginScreen
import ps.ins.activos.presentation.home.HomeScreen
import ps.ins.activos.ui.theme.AppTheme

@Composable
fun App() {

    val config = KamelConfig {
        takeFrom(KamelConfig.Default)
    }


    CompositionLocalProvider(LocalKamelConfig provides config){
        AppTheme(dynamicColor = false) {
            val settings = Settings()
            val isLoggedIn = settings.getBoolean("isLoggedIn", false)
            val initialScreen = if (isLoggedIn)  LoginScreen else HomeScreen
            Navigator(initialScreen)
        }
    }

}

