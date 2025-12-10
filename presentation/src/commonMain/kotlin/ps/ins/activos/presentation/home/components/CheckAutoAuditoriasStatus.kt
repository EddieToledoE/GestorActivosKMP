package ps.ins.activos.presentation.home.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun CheckAutoAuditoriaStatus (){
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 24.dp),
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.onErrorContainer ,
            contentColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.errorContainer
        ),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        Text(text = "Auditoría Mensual Pendiente", fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.Medium)
    }
    Text(text = "Finalizar antes de : 30 de Octubre")
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp), thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
    Spacer(modifier = Modifier.height(16.dp))
}