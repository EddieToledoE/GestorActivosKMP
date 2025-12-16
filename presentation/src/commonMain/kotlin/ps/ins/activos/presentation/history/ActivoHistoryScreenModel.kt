package ps.ins.activos.presentation.history

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ps.ins.activos.domain.activos.model.ActivoHistory
import ps.ins.activos.domain.activos.usecase.GetActivoHistoryUseCase
import ps.ins.activos.domain.core.util.onError
import ps.ins.activos.domain.core.util.onSuccess

data class ActivoHistoryState(
    val isLoading: Boolean = false,
    val history: ActivoHistory? = null,
    val error: String? = null
)

class ActivoHistoryScreenModel(
    private val getActivoHistoryUseCase: GetActivoHistoryUseCase
) : ScreenModel {

    private val _state = MutableStateFlow(ActivoHistoryState())
    val state: StateFlow<ActivoHistoryState> = _state.asStateFlow()

    fun loadHistory(id: String) {
        screenModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            getActivoHistoryUseCase(id)
                .onSuccess { history ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        history = history
                    )
                }
                .onError { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Error al cargar historial: ${error.name}"
                    )
                }
        }
    }
}
