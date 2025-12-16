package ps.ins.activos.presentation.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module
import ps.ins.activos.presentation.auth.di.authPresentationModule
import ps.ins.activos.presentation.home.di.homeModule
import ps.ins.activos.presentation.detail.di.detailModule
import ps.ins.activos.presentation.search.di.searchModule

import ps.ins.activos.presentation.transfer.di.transferModule

val presentationModule = module {
    includes(authPresentationModule, homeModule, searchModule, detailModule, transferModule)
    factory { ps.ins.activos.presentation.history.ActivoHistoryScreenModel(get()) }
    single<Settings> { Settings() }
}
