package ps.ins.activos.presentation.transfer

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ps.ins.activos.domain.core.util.onError
import ps.ins.activos.domain.core.util.onSuccess
import ps.ins.activos.domain.usuario.model.Usuario
import ps.ins.activos.domain.usuario.usecase.GetUsuarioByFortiaUseCase

data class TransferState(
    val isLoading: Boolean = false,
    val usuario: Usuario? = null,
    val error: String? = null
)

class TransferActivoScreenModel(
    private val getUsuarioByFortiaUseCase: GetUsuarioByFortiaUseCase
) : ScreenModel {

    private val _state = MutableStateFlow(TransferState())
    val state: StateFlow<TransferState> = _state.asStateFlow()

    fun searchUsuario(claveFortia: String) {
        if (claveFortia.isBlank()) return

        screenModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null, usuario = null)
            
            getUsuarioByFortiaUseCase(claveFortia)
                .onSuccess { usuario ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        usuario = usuario
                    )
                }
                .onError { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.name // Simplistic error handling
                    )
                }
        }
    }
    
    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
