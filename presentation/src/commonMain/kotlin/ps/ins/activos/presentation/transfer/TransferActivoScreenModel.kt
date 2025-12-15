package ps.ins.activos.presentation.transfer

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ps.ins.activos.domain.core.util.onError
import ps.ins.activos.domain.core.util.onSuccess
import ps.ins.activos.domain.solicitud.model.SolicitudRequest
import ps.ins.activos.domain.solicitud.usecase.CreateTransferSolicitudUseCase
import ps.ins.activos.domain.usuario.model.Usuario
import ps.ins.activos.domain.usuario.usecase.GetUsuarioByFortiaUseCase

data class TransferState(
    val isLoading: Boolean = false,
    val usuario: Usuario? = null,
    val error: String? = null,
    val isTransferring: Boolean = false,
    val transferSuccess: String? = null,
    val showConfirmDialog: Boolean = false
)

class TransferActivoScreenModel(
    private val getUsuarioByFortiaUseCase: GetUsuarioByFortiaUseCase,
    private val createTransferSolicitudUseCase: CreateTransferSolicitudUseCase,
    private val settings: Settings
) : ScreenModel {

    private val _state = MutableStateFlow(TransferState())
    val state: StateFlow<TransferState> = _state.asStateFlow()

    fun searchUsuario(claveFortia: String) {
        if (claveFortia.isBlank()) return

        screenModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null, usuario = null, transferSuccess = null)
            
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
                        error = error.name
                    )
                }
        }
    }

    fun onRequestTransfer() {
        val currentUser = _state.value.usuario ?: return
        val currentUserId = settings.getString("userId", "")
        
        if (currentUserId == currentUser.idUsuario) {
            _state.value = _state.value.copy(error = "No puedes transferir activos a ti mismo.")
            return
        }

        _state.value = _state.value.copy(showConfirmDialog = true, error = null)
    }

    fun onConfirmTransfer(activoId: String) {
        val usuario = _state.value.usuario ?: return
        val currentUserId = settings.getString("userId", "")
        _state.value = _state.value.copy(showConfirmDialog = false, isTransferring = true)

        screenModelScope.launch {
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            
            val request = SolicitudRequest(
                idEmisor = currentUserId,
                idReceptor = usuario.idUsuario,
                idActivo = activoId,
                tipo = "transferencia de activos entre usuarios",
                mensaje = "Solicitud realizada en : $today"
            )

            createTransferSolicitudUseCase(request)
                .onSuccess { response ->
                    _state.value = _state.value.copy(
                        isTransferring = false,
                        transferSuccess = response.message
                    )
                }
                .onError { error ->
                    _state.value = _state.value.copy(
                        isTransferring = false,
                        error = "Error en la transferencia: ${error.name}"
                    )
                }
        }
    }

    fun onDismissDialog() {
        _state.value = _state.value.copy(showConfirmDialog = false)
    }
    
    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
