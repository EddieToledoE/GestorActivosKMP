package ps.ins.activos.presentation.home.di

import org.koin.dsl.module
import ps.ins.activos.presentation.home.HomeScreenModel

val homeModule = module {
    factory { 
        HomeScreenModel(
            getActivosPropiosUseCase = get(),
            getSolicitudConteoUseCase = get()
        ) 
    }
}
