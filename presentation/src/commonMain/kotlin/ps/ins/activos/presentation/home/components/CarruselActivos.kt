package ps.ins.activos.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.russhwolf.settings.Settings
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ps.ins.activos.domain.activos.model.ActivoEntity
// Import or define replaceLocalhostWithCurrentIp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarruselActivos(
    activos: List<ActivoEntity>,
    modifier: Modifier = Modifier
) {
    val displayActivos = activos.take(8)
    val chunks = displayActivos.chunked(4)
    val pagerState = rememberPagerState { chunks.size }

    Column(modifier = modifier) {
        Text(
            text = "Mis Activos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (displayActivos.isEmpty()) {
             Text(
                text = "No tienes activos asignados",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
             )
        } else {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.height(420.dp).fillMaxWidth(), // Altura suficiente para 2 filas
                contentPadding = PaddingValues(16.dp),
                pageSpacing = 16.dp
            ) { pageIndex ->
                val pageItems = chunks.getOrElse(pageIndex) { emptyList() }
                
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Fila 1
                    Row(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        GridItem(pageItems.getOrNull(0))
                        GridItem(pageItems.getOrNull(1))
                    }
                    // Fila 2
                    Row(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        GridItem(pageItems.getOrNull(2))
                        GridItem(pageItems.getOrNull(3))
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.GridItem(activo: ActivoEntity?) {
    Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
        if (activo != null) {
            ActivoItem(activo, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ActivoItem(activo: ActivoEntity, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if(isSystemInDarkTheme())MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column {
            val resource = asyncPainterResource(data = replaceLocalhostWithCurrentIp(activo.imagenUrl))
            KamelImage(
                resource = {resource},
                contentDescription = activo.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // La imagen ocupa el espacio disponible
                contentScale = ContentScale.Crop,
                onFailure = { },
                onLoading = { }
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = activo.nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    EstadoBadge(activo.estado)
                }

            }
        }
    }
}

@Composable
fun EstadoBadge(estado: String) {
    val color = when (estado.lowercase()) {
        "activo" -> Color(color=0xFF4F6600).copy(alpha = 0.8f)
        "regular" -> Color.Yellow.copy(alpha = 0.2f)
        "malo" -> Color.Red.copy(alpha = 0.2f)
        else -> Color.Gray.copy(alpha = 0.2f)
    }


    Box(modifier = Modifier.clip(shape = CircleShape).size(20.dp).background(color))
        Text(
            text = estado,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )


}

// Temporary placeholder if not found, to be replaced by correct import
fun replaceLocalhostWithCurrentIp(url: String): String {
    return url.replace("localhost", Settings().getString(key = "MINIO_IP", defaultValue = "192.168.110.62")) // Example fallback
}
