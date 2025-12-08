package ps.ins.activos.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import ps.ins.activos.di.initKoin

class GestorActivosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        if (GlobalContext.getOrNull() == null) {
            initKoin {
                androidContext(this@GestorActivosApp)
            }
        }
    }
}
