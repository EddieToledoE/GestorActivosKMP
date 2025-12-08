package ps.ins.activos.presentation.auth.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ps.ins.activos.domain.auth.datasource.LoginDataSource
import ps.ins.activos.domain.auth.entities.LoginUser
import ps.ins.activos.domain.auth.entities.User
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import com.russhwolf.settings.Settings



data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null
)

class LoginScreenModel(
    private val loginDataSource: LoginDataSource,
    private val settings: Settings
) : StateScreenModel<LoginState>(LoginState()) {
    
    fun onEmailChange(email: String) {
        mutableState.value = state.value.copy(email = email, error = null)
    }
    
    fun onPasswordChange(password: String) {
        mutableState.value = state.value.copy(password = password, error = null)
    }
    
    fun onLoginClick() {
        val currentState = state.value
        
        // Validaciones básicas
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            mutableState.value = currentState.copy(
                error = "Por favor ingresa correo y contraseña"
            )
            return
        }
        
        // Iniciar proceso de login
        mutableState.value = currentState.copy(isLoading = true, error = null)
        
        screenModelScope.launch {
            val result = loginDataSource.doLogin(
                LoginUser(
                    email = currentState.email,
                    password = currentState.password
                )
            )
            
            when (result) {
                is Result.Success -> {
                    val user = result.data
                    settings.putBoolean("isLoggedIn", true)
                    settings.putString("userId", user.id) // Assuming id is Number or UUID, convert to String

                    mutableState.value = state.value.copy(
                        isLoading = false,
                        user = user,
                        error = null
                    )
                }
                is Result.Error -> {
                    mutableState.value = state.value.copy(
                        isLoading = false,
                        error = getErrorMessage(result.error)
                    )
                }
            }
        }
    }
    
    private fun getErrorMessage(error: ps.ins.activos.domain.core.util.NetworkError): String {
        return when (error) {
            NetworkError.REQUEST_TIMEOUT ->
                "Tiempo de espera agotado. Verifica tu conexión."
            NetworkError.NO_INTERNET ->
                "Sin conexión a internet"
            NetworkError.SERVER_ERROR ->
                "Error en el servidor. Intenta más tarde."
            NetworkError.SERIALIZATION ->
                "Error procesando la respuesta"
            NetworkError.TOO_MANY_REQUESTS ->
                "Demasiados intentos. Por favor espera un momento."
            NetworkError.INCORRECT_CREDENTIALS ->
                "Correo o contraseña incorrectos"
            NetworkError.UNKNOWN ->
                "Error desconocido. Intenta nuevamente."
            NetworkError.BAD_REQUEST ->
                "Solicitud Incorrecta, revisa los datos enviados"
            NetworkError.INTERNAL_SERVER_ERROR ->
                "Error del servidor, Intentelo mas tarde"
        }
    }
}
