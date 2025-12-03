package ps.ins.activos.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import gestoractivos.composeapp.generated.resources.LogoMain
import gestoractivos.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

actual object AppImages {
    @Composable
    actual val logoMain: Painter
        get() = painterResource(Res.drawable.LogoMain)
}