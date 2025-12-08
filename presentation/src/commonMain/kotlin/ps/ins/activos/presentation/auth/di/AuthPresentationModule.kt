package ps.ins.activos.presentation.auth.di

import org.koin.dsl.module
import ps.ins.activos.presentation.auth.login.LoginScreenModel

val authPresentationModule = module {
    // ScreenModels como factory (nueva instancia cada vez)
    factory { LoginScreenModel(loginDataSource = get(), settings = get()) }
}
