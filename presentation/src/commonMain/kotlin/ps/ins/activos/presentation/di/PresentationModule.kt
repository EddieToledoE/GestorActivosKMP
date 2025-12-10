package ps.ins.activos.presentation.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module
import ps.ins.activos.presentation.auth.di.authPresentationModule
import ps.ins.activos.presentation.home.di.homeModule

val presentationModule = module {
    includes(authPresentationModule, homeModule)
    single<Settings> { Settings() }
}
