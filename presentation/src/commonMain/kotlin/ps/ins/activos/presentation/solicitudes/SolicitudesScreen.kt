package ps.ins.activos.presentation.solicitudes

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ps.ins.activos.domain.solicitud.model.SolicitudDetail

object SolicitudesScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<SolicitudListScreenModel>()
        val state by screenModel.state.collectAsState()

        var selectedTab by remember { mutableStateOf(0) }
        val tabs = listOf("Entrantes", "Salientes")

        LaunchedEffect(selectedTab) {
            val type = if (selectedTab == 0) SolicitudType.RECIBIDAS else SolicitudType.ENVIADAS
            screenModel.loadSolicitudes(type)
        }

        var showAcceptDialog by remember { mutableStateOf<SolicitudDetail?>(null) }
        var showRejectDialog by remember { mutableStateOf<SolicitudDetail?>(null) }
        var rejectReason by remember { mutableStateOf("") }

        if (showAcceptDialog != null) {
            AlertDialog(
                onDismissRequest = { showAcceptDialog = null },
                title = { Text("Confirmar Aprobación") },
                text = { Text("¿Estás seguro de que deseas aprobar la solicitud de ${showAcceptDialog?.nombreEmisor}?") },
                confirmButton = {
                    TextButton(onClick = {
                        showAcceptDialog?.let { solicitud ->
                            screenModel.acceptSolicitud(solicitud.idSolicitud) {
                                showAcceptDialog = null
                            }
                        }
                    }) {
                        Text("Aprobar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAcceptDialog = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        if (showRejectDialog != null) {
            AlertDialog(
                onDismissRequest = { showRejectDialog = null },
                title = { Text("Rechazar Solicitud") },
                text = {
                    Column {
                        Text("Por favor ingresa el motivo del rechazo:")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = rejectReason,
                            onValueChange = { rejectReason = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Motivo") }
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        showRejectDialog?.let { solicitud ->
                            if (rejectReason.isNotBlank()) {
                                screenModel.rejectSolicitud(solicitud.idSolicitud, rejectReason) {
                                    showRejectDialog = null
                                    rejectReason = ""
                                }
                            }
                        }
                    }) {
                        Text("Rechazar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { 
                        showRejectDialog = null 
                        rejectReason = ""
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Solicitudes") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else White
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else Color(0xFFF5F5F5))
            ) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else White,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { 
                                val count = if (index == 0) state.entrantesCount else state.salientesCount
                                Text("$title ($count)") 
                            }
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                     if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else if (state.error != null) {
                        Text(
                            text = state.error ?: "Error desconocido",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        if (state.solicitudes.isEmpty()) {
                            Text(
                                text = "No hay solicitudes ${if(selectedTab == 0) "entrantes" else "salientes"}",
                                modifier = Modifier.align(Alignment.Center),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize().padding(16.dp)
                            ) {
                                items(state.solicitudes) { solicitud ->
                                    val type = if (selectedTab == 0) SolicitudType.RECIBIDAS else SolicitudType.ENVIADAS
                                    SolicitudCard(
                                        solicitud = solicitud, 
                                        type = type,
                                        onAccept = { showAcceptDialog = solicitud },
                                        onReject = { showRejectDialog = solicitud }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SolicitudCard(
        solicitud: SolicitudDetail, 
        type: SolicitudType,
        onAccept: () -> Unit,
        onReject: () -> Unit
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF2C2C2C) else White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (type == SolicitudType.ENVIADAS) "Para: ${solicitud.nombreReceptor}" else "De: ${solicitud.nombreEmisor}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = solicitud.fecha.take(10), 
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = solicitud.estado,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                Text(text = "Activo:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "${solicitud.descripcionActivo} (${solicitud.etiquetaActivo})", style = MaterialTheme.typography.bodyMedium)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                if (solicitud.mensaje.isNotBlank()) {
                     Text(text = "Mensaje:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                     Text(text = solicitud.mensaje, style = MaterialTheme.typography.bodySmall, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                }

                if (type == SolicitudType.RECIBIDAS && solicitud.estado == "Pendiente") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = onReject,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Rechazar")
                        }
                        Button(
                            onClick = onAccept,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Aceptar")
                        }
                    }
                } else if (type == SolicitudType.ENVIADAS) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if(solicitud.estado == "Pendiente") "Esperando respuesta del receptor..." else "Solicitud ${solicitud.estado}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}
