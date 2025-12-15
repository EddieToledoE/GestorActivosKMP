package ps.ins.activos.presentation.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ps.ins.activos.domain.activos.model.ActivoEntity
import ps.ins.activos.domain.usuario.model.Usuario

data class TransferActivoScreen(val activo: ActivoEntity) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<TransferActivoScreenModel>()
        val state by screenModel.state.collectAsState()
        
        var searchText by remember { mutableStateOf("") }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Transferir Activo") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else Color.White
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Asset Summary
                Card(
                     modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                     colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                     )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Activo a transferir", style = MaterialTheme.typography.labelMedium)
                        Text(text = activo.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "Etiqueta: ${activo.etiqueta}", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Text(
                    text = "Buscar nuevo responsable",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Search Bar
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Clave Fortia") },
                    placeholder = { Text("Ej: 223191") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = { screenModel.searchUsuario(searchText) }
                    ),
                    trailingIcon = {
                        IconButton(onClick = { screenModel.searchUsuario(searchText) }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                // Results Area
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else if (state.error != null) {
                        Text(
                            text = "Error al buscar: ${state.error}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else if (state.usuario != null) {
                        UsuarioDetailCard(state.usuario!!)
                    }
                }
            }
        }
    }
    
    @Composable
    fun UsuarioDetailCard(usuario: Usuario) {
         Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Resultados de búsqueda", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                
                DetailItem("Nombre", usuario.nombreCompleto)
                DetailItem("Clave", usuario.claveFortia)
                DetailItem("Área", usuario.area)
                DetailItem("Ubicación", usuario.ubicacion)
                DetailItem("Razón Social", usuario.razonSocial)
                
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Implement transfer confirmation logic later */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar Transferencia")
                }
            }
        }
    }
    
    @Composable
    fun DetailItem(label: String, value: String) {
        Row(modifier = Modifier.padding(vertical = 4.dp)) {
            Text(text = "$label: ", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Text(text = value, modifier = Modifier.weight(2f))
        }
    }
}
