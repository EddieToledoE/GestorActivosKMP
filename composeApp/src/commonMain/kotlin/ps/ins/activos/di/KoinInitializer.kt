package ps.ins.activos.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import ps.ins.activos.data.di.dataModule
import ps.ins.activos.domain.di.domainModule
import ps.ins.activos.presentation.di.presentationModule

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            domainModule,
            dataModule,
            presentationModule
        )
    }
}
