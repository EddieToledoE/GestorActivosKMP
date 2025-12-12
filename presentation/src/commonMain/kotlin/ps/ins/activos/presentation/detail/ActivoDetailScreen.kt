package ps.ins.activos.presentation.detail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ps.ins.activos.domain.activos.model.ActivoEntity
import ps.ins.activos.presentation.home.components.replaceLocalhostWithCurrentIp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.koinScreenModel


data class ActivoDetailScreen(val activo: ActivoEntity) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<ActivoDetailScreenModel>()
        val state by screenModel.state.collectAsState()

        androidx.compose.runtime.LaunchedEffect(activo.idActivo) {
            screenModel.loadDetail(activo.idActivo)
        }
        
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else Color(0xFFF5F5F5))
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header Image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        val resource = asyncPainterResource(data = replaceLocalhostWithCurrentIp(activo.imagenUrl))
                        KamelImage(
                            resource = { resource },
                            contentDescription = activo.nombre,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            onLoading = { Box(Modifier.fillMaxSize().background(Color.Gray)) },
                            onFailure = { Box(Modifier.fillMaxSize().background(Color.DarkGray)) }
                        )
                        
                        // Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                        startY = 100f
                                    )
                                )
                        )
                        
                        // Back Button
                        IconButton(
                            onClick = { navigator.pop() },
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.TopStart)
                                .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(50))
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                        
                        // Title Overlay
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(24.dp)
                        ) {
                             val etiquetaText = if (activo.isPropio) "Propio" else activo.centroCosto
                             val etiquetaColor = if (activo.isPropio) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                            
                            Text(
                                text = etiquetaText,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(etiquetaColor)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = activo.nombre,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    // Details Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            DetailRow("Marca", activo.marca)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                            DetailRow("Modelo", activo.modelo)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                            DetailRow("Categoría", activo.categoria)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                            DetailRow("Etiqueta", activo.etiqueta)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                            DetailRow("Responsable", activo.responsable)
                            
                            // Extra Details
                             if (state.isLoading) {
                                 Spacer(modifier = Modifier.height(16.dp))
                                 Text("Cargando detalles...", style = MaterialTheme.typography.bodySmall)
                             } else if (state.detail != null) {
                                  val detail = state.detail!!
                                  HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), thickness = 2.dp)
                                  
                                  DetailRow("Descripción", detail.descripcion)
                                  HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                  
                                  DetailRow("No. Serie", detail.numeroSerie)
                                  HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                  
                                  DetailRow("Fecha Alta", detail.fechaAlta)
                                  HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                  
                                  DetailRow("Porta Etiqueta", if (detail.portaEtiqueta) "Sí" else "No")

                                  if (!detail.donacion) {
                                      HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                      DetailRow("Cuenta Contable", detail.cuentaContable ?: "-")
                                      
                                      HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                      DetailRow("Valor Adquisición", detail.valorAdquisicion?.toString() ?: "-")

                                      HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                      DetailRow("Fecha Adquisición", detail.fechaAdquisicion ?: "-")
                                  }
                             }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }

    @Composable
    fun DetailRow(label: String, value: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                 modifier = Modifier.weight(2f)
            )
        }
    }
}
