package ps.ins.activos.presentation.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module
import ps.ins.activos.presentation.auth.di.authPresentationModule

val presentationModule = module {
    includes(authPresentationModule)
    single<Settings> { Settings() }
}
