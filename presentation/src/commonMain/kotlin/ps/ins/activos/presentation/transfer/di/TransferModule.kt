package ps.ins.activos.presentation.transfer.di

import org.koin.dsl.module
import ps.ins.activos.presentation.solicitudes.SolicitudListScreenModel
import ps.ins.activos.presentation.transfer.TransferActivoScreenModel

val transferModule = module {
    factory { 
        TransferActivoScreenModel(
            getUsuarioByFortiaUseCase = get(),
            createTransferSolicitudUseCase = get(),
            settings = get()
        ) 
    }
    factory {
        SolicitudListScreenModel(
            getSolicitudesEnviadasUseCase = get(),
            getSolicitudesRecibidasUseCase = get(),
            acceptSolicitudUseCase = get(),
            rejectSolicitudUseCase = get(),
            settings = get()
        )
    }
}
