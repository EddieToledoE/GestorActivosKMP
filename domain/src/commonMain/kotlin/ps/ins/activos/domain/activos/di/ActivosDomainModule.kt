package ps.ins.activos.domain.activos.di

import org.koin.dsl.module
import ps.ins.activos.domain.activos.usecase.GetActivosPropiosUseCase

val activosDomainModule = module {
    factory { GetActivosPropiosUseCase(repository = get()) }
    factory { ps.ins.activos.domain.activos.usecase.GetAllActivosUseCase(repository = get()) }
}
