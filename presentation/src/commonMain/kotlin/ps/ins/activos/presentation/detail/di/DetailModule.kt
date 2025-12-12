package ps.ins.activos.presentation.detail.di

import org.koin.dsl.module
import ps.ins.activos.presentation.detail.ActivoDetailScreenModel

val detailModule = module {
    factory { 
        ActivoDetailScreenModel(
            getActivoDetailUseCase = get(),
            permissionManager = get()
        ) 
    }
}
