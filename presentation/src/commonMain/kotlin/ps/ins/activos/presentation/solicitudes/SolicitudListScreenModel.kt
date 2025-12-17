package ps.ins.activos.presentation.solicitudes

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ps.ins.activos.domain.core.util.onError
import ps.ins.activos.domain.core.util.onSuccess
import ps.ins.activos.domain.solicitud.model.SolicitudDetail
import ps.ins.activos.domain.solicitud.usecase.AcceptSolicitudUseCase
import ps.ins.activos.domain.solicitud.usecase.GetSolicitudesEnviadasUseCase
import ps.ins.activos.domain.solicitud.usecase.GetSolicitudesRecibidasUseCase
import ps.ins.activos.domain.solicitud.usecase.RejectSolicitudUseCase
import ps.ins.activos.domain.solicitud.usecase.GetSolicitudConteoUseCase

enum class SolicitudType {
    ENVIADAS, RECIBIDAS
}

data class SolicitudListState(
    val isLoading: Boolean = false,
    val solicitudes: List<SolicitudDetail> = emptyList(),
    val error: String? = null,
    val entrantesCount: Int = 0,
    val salientesCount: Int = 0
)

class SolicitudListScreenModel(
    private val getSolicitudesEnviadasUseCase: GetSolicitudesEnviadasUseCase,
    private val getSolicitudesRecibidasUseCase: GetSolicitudesRecibidasUseCase,
    private val acceptSolicitudUseCase: AcceptSolicitudUseCase,
    private val rejectSolicitudUseCase: RejectSolicitudUseCase,
    private val getSolicitudConteoUseCase: GetSolicitudConteoUseCase,
    private val settings: Settings
) : ScreenModel {

    private val _state = MutableStateFlow(SolicitudListState())
    val state: StateFlow<SolicitudListState> = _state.asStateFlow()

    fun loadSolicitudes(type: SolicitudType) {
        val userId = settings.getString("userId", "")
        if (userId.isBlank()) {
            _state.value = _state.value.copy(error = "Usuario no identificado")
            return
        }

        screenModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            // Load counts
            getSolicitudConteoUseCase().onSuccess { conteo ->
                 _state.value = _state.value.copy(
                     entrantesCount = conteo.entrantes,
                     salientesCount = conteo.salientes
                 )
            }

            val result = when (type) {
                SolicitudType.ENVIADAS -> getSolicitudesEnviadasUseCase(userId)
                SolicitudType.RECIBIDAS -> getSolicitudesRecibidasUseCase(userId)
            }

            result
                .onSuccess { list ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        solicitudes = list
                    )
                }
                .onError { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Error al cargar solicitudes: ${error.name}"
                    )
                }
        }
    }

    fun acceptSolicitud(idSolicitud: String, onSuccess: () -> Unit) {
        val userId = settings.getString("userId", "")
        screenModelScope.launch {
             _state.value = _state.value.copy(isLoading = true)
             acceptSolicitudUseCase(idSolicitud, userId)
                 .onSuccess {
                     // Reload list
                     loadSolicitudes(SolicitudType.RECIBIDAS)
                     onSuccess()
                 }
                 .onError { error ->
                      _state.value = _state.value.copy(isLoading = false, error = "Error al aceptar: ${error.name}")
                 }
        }
    }

    fun rejectSolicitud(idSolicitud: String, motivo: String, onSuccess: () -> Unit) {
        val userId = settings.getString("userId", "")
        screenModelScope.launch {
             _state.value = _state.value.copy(isLoading = true)
             rejectSolicitudUseCase(idSolicitud, userId, motivo)
                 .onSuccess {
                     // Reload list
                     loadSolicitudes(SolicitudType.RECIBIDAS)
                     onSuccess()
                 }
                 .onError { error ->
                      _state.value = _state.value.copy(isLoading = false, error = "Error al rechazar: ${error.name}")
                 }
        }
    }
}
