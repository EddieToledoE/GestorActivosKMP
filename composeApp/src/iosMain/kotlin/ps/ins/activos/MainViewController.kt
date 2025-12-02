package ps.ins.activos

import androidx.compose.ui.window.ComposeUIViewController
import ps.ins.activos.di.initKoin

fun MainViewController() = ComposeUIViewController { 
    initKoin()
    App() 
}