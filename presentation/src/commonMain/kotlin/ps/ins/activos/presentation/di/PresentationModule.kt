package ps.ins.activos.presentation.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module
import ps.ins.activos.presentation.auth.di.authPresentationModule
import ps.ins.activos.presentation.home.di.homeModule
import ps.ins.activos.presentation.detail.di.detailModule
import ps.ins.activos.presentation.search.di.searchModule

val presentationModule = module {
    includes(authPresentationModule, homeModule, searchModule, detailModule)
    single<Settings> { Settings() }
}
