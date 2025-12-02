package ps.ins.activos.data.di

import org.koin.dsl.module
import ps.ins.activos.data.auth.di.authDataModule

val dataModule = module {
    includes(authDataModule)
}