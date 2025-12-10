@file:OptIn(ExperimentalMaterial3Api::class)

package ps.ins.activos.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.russhwolf.settings.Settings
import gestoractivos.presentation.generated.resources.Res
import gestoractivos.presentation.generated.resources.logomain
import org.jetbrains.compose.resources.painterResource


@Composable
fun TopAppBar(onLogoutClick : ()-> Unit) {
    TopAppBar(
        title = {
            Column {
                Text(text = "Hola, ${Settings().getString(key = "fullName", defaultValue = "Alex Winter")}")
                Text(text = "Bienvenido al gestor de activos", fontSize = MaterialTheme.typography.bodyLarge.fontSize,fontWeight = FontWeight.Light )
            }
          },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor =  if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else  Color(0xFFE3E3E3)
        ),
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = (Icons.AutoMirrored.Outlined.Article),
                    contentDescription = "Message",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = (Icons.AutoMirrored.Outlined.Logout),
                    contentDescription = "Log Out",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
    )
}