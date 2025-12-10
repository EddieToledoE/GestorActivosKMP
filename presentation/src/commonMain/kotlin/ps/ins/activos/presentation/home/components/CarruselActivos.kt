package ps.ins.activos.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.russhwolf.settings.Settings
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ps.ins.activos.domain.activos.model.ActivoEntity
// Import or define replaceLocalhostWithCurrentIp

@Composable
fun CarruselActivos(
    activos: List<ActivoEntity>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Mis Activos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        KamelImage(
            resource = { asyncPainterResource( data = "https://res.cloudinary.com/dwjnodqln/image/upload/v1699737730/i8xvwjujmexnqhb39xvh.jpg") },
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            alpha = DefaultAlpha,
            contentAlignment = Alignment.Center
        )
        if (activos.isEmpty()) {
             Text(
                text = "No tienes activos asignados",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
             )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(activos) { activo ->
                    ActivoItem(activo)
                }
            }
        }
    }
}

@Composable
fun ActivoItem(activo: ActivoEntity) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.width(160.dp)
    ) {
        Column {
            val resource = asyncPainterResource(data = "https://res.cloudinary.com/dwjnodqln/image/upload/v1699737730/i8xvwjujmexnqhb39xvh.jpg")
            KamelImage(
                resource = { resource },
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                alpha = DefaultAlpha,
                contentAlignment = Alignment.Center
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
                
                EstadoBadge(activo.estado)
            }
        }
    }
}

@Composable
fun EstadoBadge(estado: String) {
    val color = when (estado.lowercase()) {
        "bueno" -> Color.Green.copy(alpha = 0.2f)
        "regular" -> Color.Yellow.copy(alpha = 0.2f)
        "malo" -> Color.Red.copy(alpha = 0.2f)
        else -> Color.Gray.copy(alpha = 0.2f)
    }
    
    val textColor = when (estado.lowercase()) {
        "bueno" -> Color(0xFF006400)
        "regular" -> Color(0xFF8B8000)
        "malo" -> Color.Red
        else -> Color.Gray
    }

    Text(
        text = estado,
        style = MaterialTheme.typography.labelSmall,
        color = textColor,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}

// Temporary placeholder if not found, to be replaced by correct import
fun replaceLocalhostWithCurrentIp(url: String): String {
    return url.replace("localhost", Settings().getString(key = "MINIO_IP", defaultValue = "192.168.110.62")) // Example fallback
}
