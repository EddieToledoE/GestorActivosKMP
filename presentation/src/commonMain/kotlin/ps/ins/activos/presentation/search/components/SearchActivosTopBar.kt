package ps.ins.activos.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchActivoTopBar (onBackClick: () -> Unit){
    TopAppBar(
        modifier = Modifier.background( color = if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else  Color(0xFFE3E3E3)),
        title = {},
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atras", tint = MaterialTheme.colorScheme.primary)
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor =  if (isSystemInDarkTheme()) Color(0xFF1C1B1C) else  Color(0xFFE3E3E3)
        ),
    )
}