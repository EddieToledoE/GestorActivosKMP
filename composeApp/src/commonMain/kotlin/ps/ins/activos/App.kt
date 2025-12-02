package ps.ins.activos


import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ps.ins.activos.presentation.auth.login.LoginScreen
import ps.ins.activos.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme(dynamicColor = true) {
        Navigator(LoginScreen())
    }
}
