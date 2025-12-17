package ps.ins.activos.data.solicitud.di

import org.koin.dsl.module
import ps.ins.activos.data.solicitud.networking.KtorRemoteSolicitudDataSource
import ps.ins.activos.data.solicitud.networking.RemoteSolicitudDataSource
import ps.ins.activos.data.solicitud.repository.SolicitudRepositoryImpl
import ps.ins.activos.domain.solicitud.repository.SolicitudRepository
import ps.ins.activos.domain.solicitud.usecase.AcceptSolicitudUseCase
import ps.ins.activos.domain.solicitud.usecase.CreateTransferSolicitudUseCase
import ps.ins.activos.domain.solicitud.usecase.GetSolicitudesEnviadasUseCase
import ps.ins.activos.domain.solicitud.usecase.GetSolicitudesRecibidasUseCase
import ps.ins.activos.domain.solicitud.usecase.RejectSolicitudUseCase
import ps.ins.activos.domain.solicitud.usecase.GetSolicitudConteoUseCase

val solicitudDataModule = module {
    single<RemoteSolicitudDataSource> {
        KtorRemoteSolicitudDataSource(
            httpClient = get(),
            settings = get()
        )
    }

    single<SolicitudRepository> {
        SolicitudRepositoryImpl(
            remoteDataSource = get()
        )
    }

    factory { CreateTransferSolicitudUseCase(get()) }
    factory { GetSolicitudesEnviadasUseCase(get()) }
    factory { GetSolicitudesRecibidasUseCase(get()) }
    factory { AcceptSolicitudUseCase(get()) }
    factory { RejectSolicitudUseCase(get()) }
    factory { GetSolicitudConteoUseCase(get()) }
}
