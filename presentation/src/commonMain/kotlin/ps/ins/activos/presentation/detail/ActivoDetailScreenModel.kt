package ps.ins.activos.presentation.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ps.ins.activos.domain.activos.model.ActivoDetail
import ps.ins.activos.domain.activos.usecase.GetActivoDetailUseCase
import ps.ins.activos.domain.core.util.onError
import ps.ins.activos.domain.core.util.onSuccess
import ps.ins.activos.domain.permissions.PermissionManager

data class ActivoDetailState(
    val detail: ActivoDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasPermission: Boolean = false
)

class ActivoDetailScreenModel(
    private val getActivoDetailUseCase: GetActivoDetailUseCase,
    private val permissionManager: PermissionManager
) : StateScreenModel<ActivoDetailState>(ActivoDetailState()) {

    init {
        checkPermission()
    }

    private fun checkPermission() {
        screenModelScope.launch {
            permissionManager.permissions.collectLatest { permissions ->
                val hasPerm = permissions.contains("Activo_Ver_Detalle")
                mutableState.value = mutableState.value.copy(hasPermission = hasPerm)
            }
        }
    }

    fun loadDetail(id: String) {
        if (!state.value.hasPermission) return

        mutableState.value = mutableState.value.copy(isLoading = true)
        screenModelScope.launch {
            getActivoDetailUseCase(id)
                .onSuccess { detail ->
                    mutableState.value = mutableState.value.copy(
                        detail = detail,
                        isLoading = false,
                        error = null
                    )
                }
                .onError { error ->
                    mutableState.value = mutableState.value.copy(
                        isLoading = false,
                        error = error.name // Simplification
                    )
                }
        }
    }
}
