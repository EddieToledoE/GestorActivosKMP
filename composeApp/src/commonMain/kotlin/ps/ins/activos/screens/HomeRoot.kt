package ps.ins.activos.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import org.koin.compose.koinInject
import ps.ins.activos.presentation.home.HomeScreenContent

object HomeRoot : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings = koinInject<Settings>()

        HomeScreenContent(
            onLogoutClick = {
                settings.clear()
                navigator.replaceAll(LoginRoot)
            }
        )
    }
}
