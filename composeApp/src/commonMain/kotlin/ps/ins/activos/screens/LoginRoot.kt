package ps.ins.activos.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.ui.tooling.preview.Preview
import ps.ins.activos.presentation.auth.login.LoginScreenContent
import ps.ins.activos.presentation.auth.login.LoginScreenModel
import ps.ins.activos.presentation.auth.login.LoginState
import ps.ins.activos.resources.SharedImages
import ps.ins.activos.ui.theme.AppTheme


object LoginRoot : Screen{
    @Composable
    override fun Content(){
        val navigator = cafe.adriel.voyager.navigator.LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<LoginScreenModel>()
        val state by screenModel.state.collectAsState()

        androidx.compose.runtime.LaunchedEffect(state.user) {
            if (state.user != null) {
                navigator.replaceAll(HomeRoot)
            }
        }

        LoginScreenContent(
            state = state,
            onEmailChange = screenModel::onEmailChange,
            onPasswordChange = screenModel::onPasswordChange,
            onLoginClick = screenModel::onLoginClick,
            logoPainter = SharedImages.logoMain()
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppTheme(dynamicColor = false) {
        LoginScreenContent(
            state = LoginState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            logoPainter = SharedImages.logoMain() // Painter válido para preview
        )
    }
}
