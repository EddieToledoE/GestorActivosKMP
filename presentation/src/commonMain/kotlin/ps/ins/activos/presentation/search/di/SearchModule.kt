package ps.ins.activos.presentation.search.di

import org.koin.dsl.module
import ps.ins.activos.presentation.search.SearchScreenModel

val searchModule = module {
    factory { 
        SearchScreenModel(
            getAllActivosUseCase = get()
        ) 
    }
}
