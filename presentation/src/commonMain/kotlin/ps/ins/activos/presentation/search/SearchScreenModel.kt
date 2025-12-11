package ps.ins.activos.presentation.search

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ps.ins.activos.domain.activos.model.ActivoEntity
import ps.ins.activos.domain.activos.usecase.GetAllActivosUseCase
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

data class SearchState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val allActivos: List<ActivoEntity> = emptyList(),
    val filteredActivos: List<ActivoEntity> = emptyList(),
    val searchQuery: String = "",
    val availableCategories: List<String> = emptyList(),
    val selectedCategory: String? = null
)

class SearchScreenModel(
    private val getAllActivosUseCase: GetAllActivosUseCase
) : StateScreenModel<SearchState>(SearchState()) {

    init {
        loadActivos()
    }

    fun loadActivos() {
        mutableState.update { it.copy(isLoading = true, error = null) }
        screenModelScope.launch {
            when (val result = getAllActivosUseCase()) {
                is Result.Success -> {
                    val categories = result.data.map { it.categoria }.distinct().sorted()
                    mutableState.update { 
                        it.copy(
                            isLoading = false,
                            allActivos = result.data,
                            filteredActivos = result.data,
                            availableCategories = categories
                        )
                    }
                }
                is Result.Error -> {
                    mutableState.update { 
                        it.copy(
                            isLoading = false,
                            error = getErrorMessage(result.error)
                        )
                    }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        mutableState.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    fun onCategorySelected(category: String) {
        val newSelection = if (state.value.selectedCategory == category) null else category
        mutableState.update { it.copy(selectedCategory = newSelection) }
        applyFilters()
    }

    private fun applyFilters() {
        val query = state.value.searchQuery
        val category = state.value.selectedCategory
        
        val filtered = state.value.allActivos.filter { activo ->
            val matchesQuery = if (query.isBlank()) true else {
                activo.nombre.contains(query, ignoreCase = true) || 
                activo.modelo.contains(query, ignoreCase = true) ||
                activo.categoria.contains(query, ignoreCase = true) ||
                activo.centroCosto.contains(query, ignoreCase = true)
            }
            val matchesCategory = if (category == null) true else {
                activo.categoria == category
            }
            matchesQuery && matchesCategory
        }
        
        mutableState.update { it.copy(filteredActivos = filtered) }
    }

    private fun getErrorMessage(error: NetworkError): String {
        return when (error) {
            NetworkError.REQUEST_TIMEOUT -> "Tiempo de espera agotado"
            NetworkError.NO_INTERNET -> "Sin conexión a internet"
            NetworkError.SERVER_ERROR -> "Error en el servidor"
            else -> "Error desconocido"
        }
    }
}
