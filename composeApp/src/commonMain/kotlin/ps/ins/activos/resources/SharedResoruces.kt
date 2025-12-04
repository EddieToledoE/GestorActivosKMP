package ps.ins.activos.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import gestoractivos.composeapp.generated.resources.Res
import gestoractivos.composeapp.generated.resources.logomain
import org.jetbrains.compose.resources.painterResource

object SharedImages {
    @Composable
    fun logoMain(): Painter = painterResource(Res.drawable.logomain)
}