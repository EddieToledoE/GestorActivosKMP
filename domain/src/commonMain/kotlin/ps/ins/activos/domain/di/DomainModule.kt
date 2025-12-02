package ps.ins.activos.domain.di

import org.koin.dsl.module
import ps.ins.activos.domain.auth.di.authDomainModule

val domainModule = module {
    includes(authDomainModule)
}
