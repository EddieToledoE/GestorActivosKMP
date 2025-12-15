package ps.ins.activos.presentation.transfer.di

import org.koin.dsl.module
import ps.ins.activos.presentation.transfer.TransferActivoScreenModel

val transferModule = module {
    factory { TransferActivoScreenModel(get()) }
}
