package ps.ins.activos.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import ps.ins.activos.presentation.search.components.SearchActivoTopBar
import ps.ins.activos.presentation.search.components.SearchActivosCard
import ps.ins.activos.presentation.search.components.SearchActivosTextField
import ps.ins.activos.presentation.search.components.CategoryFilterRow

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

object SearchScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<SearchScreenModel>()
        val state by screenModel.state.collectAsState()

        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .background( color = if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else  Color(0xFFE3E3E3))
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.TopCenter,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SearchActivoTopBar(onBackClick = { navigator.pop() })
                    SearchActivosTextField(
                        query = state.searchQuery, 
                        onQueryChange = screenModel::onSearchQueryChange
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp))
                    
                    CategoryFilterRow(
                        categories = state.availableCategories,
                        selectedCategory = state.selectedCategory,
                        onCategorySelected = screenModel::onCategorySelected
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    if (state.isLoading) {
                        // Optional: Show loading indicator
                        Text("Cargando...", modifier = Modifier.padding(16.dp))
                    } else if (state.error != null) {
                        Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(state.filteredActivos) { activo ->
                                SearchActivosCard(
                                    activo = activo,
                                    onClick = { navigator.push(ps.ins.activos.presentation.detail.ActivoDetailScreen(activo)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
