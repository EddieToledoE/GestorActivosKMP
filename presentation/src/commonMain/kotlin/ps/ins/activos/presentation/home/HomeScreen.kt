package ps.ins.activos.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import org.koin.compose.koinInject
import ps.ins.activos.presentation.auth.login.LoginScreen
import ps.ins.activos.presentation.home.components.CheckAutoAuditoriaStatus
import ps.ins.activos.presentation.home.components.QuickView
import ps.ins.activos.presentation.home.components.SearchActivos
import ps.ins.activos.presentation.home.components.TopAppBar

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings = koinInject<Settings>()

        HomeScreenContent(
            onLogoutClick = {
                settings.clear()
                navigator.replaceAll(LoginScreen)
            }
        )
    }
}

@Composable
fun HomeScreenContent(
    onLogoutClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .background( color = if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else  Color(0xFFE3E3E3))
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TopAppBar(onLogoutClick)
                Spacer(modifier = Modifier.height(16.dp))
                SearchActivos()
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp), thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                CheckAutoAuditoriaStatus()
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp), thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                QuickView()

            }
        }
    }
}
