package ps.ins.activos.data.di

import org.koin.dsl.module
import ps.ins.activos.data.auth.di.authDataModule
import ps.ins.activos.data.activos.di.activosDataModule

val dataModule = module {
    includes(authDataModule, activosDataModule)
}