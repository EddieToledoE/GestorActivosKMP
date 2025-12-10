package ps.ins.activos.domain.di

import org.koin.dsl.module
import ps.ins.activos.domain.auth.di.authDomainModule
import ps.ins.activos.domain.activos.di.activosDomainModule

val domainModule = module {
    includes(authDomainModule, activosDomainModule)
}
