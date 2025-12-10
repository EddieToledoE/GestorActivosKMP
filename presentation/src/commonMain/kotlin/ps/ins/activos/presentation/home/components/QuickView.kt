package ps.ins.activos.presentation.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun QuickView (){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxWidth(). padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ){
        Text(text = "Vistazo Rápido", fontSize = 24.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
    }

}
