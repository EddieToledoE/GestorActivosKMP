package ps.ins.activos.presentation.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ps.ins.activos.domain.activos.model.ActivoEntity
import ps.ins.activos.domain.activos.usecase.GetActivosPropiosUseCase
import ps.ins.activos.domain.solicitud.usecase.GetSolicitudConteoUseCase
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result
import ps.ins.activos.domain.core.util.onSuccess

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val activos: List<ActivoEntity> = emptyList(),
    val notificationCount: Int = 0
)

class HomeScreenModel(
    private val getActivosPropiosUseCase: GetActivosPropiosUseCase,
    private val getSolicitudConteoUseCase: GetSolicitudConteoUseCase
) : StateScreenModel<HomeState>(HomeState()) {

    init {
        getActivosPropios()
        getNotificationCount()
    }
    
    fun getNotificationCount() {
        screenModelScope.launch {
            getSolicitudConteoUseCase().onSuccess { conteo ->
                 mutableState.value = state.value.copy(notificationCount = conteo.total)
            }
        }
    }

    fun getActivosPropios() {
        mutableState.value = state.value.copy(isLoading = true, error = null)
        screenModelScope.launch {
            when (val result = getActivosPropiosUseCase()) {
                is Result.Success -> {
                    mutableState.value = state.value.copy(
                        isLoading = false,
                        activos = result.data
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
    
    private fun getErrorMessage(error: NetworkError): String {
       // Reusing error message logic (can be extracted to util later)
       return when (error) {
            NetworkError.REQUEST_TIMEOUT -> "Tiempo de espera agotado"
            NetworkError.NO_INTERNET -> "Sin conexión a internet"
            NetworkError.SERVER_ERROR -> "Error en el servidor"
            else -> "Error desconocido"
        }
    }
}
