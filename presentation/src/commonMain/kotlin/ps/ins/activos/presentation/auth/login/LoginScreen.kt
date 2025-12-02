package ps.ins.activos.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<LoginScreenModel>()
        val state by screenModel.state.collectAsState()
        
        LoginScreenContent(
            state = state,
            onEmailChange = screenModel::onEmailChange,
            onPasswordChange = screenModel::onPasswordChange,
            onLoginClick = screenModel::onLoginClick
        )
    }
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Inicio de Sesión",
                    style = MaterialTheme.typography.headlineMedium
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    singleLine = true
                )
                
                if (state.error != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Entrar")
                    }
                }
                
                // Debug: Mostrar usuario si login exitoso
                if (state.user != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Bienvenido, ${state.user.fullName}!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
