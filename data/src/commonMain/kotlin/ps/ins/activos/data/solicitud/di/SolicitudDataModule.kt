package ps.ins.activos.data.solicitud.di

import org.koin.dsl.module
import ps.ins.activos.data.solicitud.networking.KtorRemoteSolicitudDataSource
import ps.ins.activos.data.solicitud.networking.RemoteSolicitudDataSource
import ps.ins.activos.data.solicitud.repository.SolicitudRepositoryImpl
import ps.ins.activos.domain.solicitud.repository.SolicitudRepository
import ps.ins.activos.domain.solicitud.usecase.CreateTransferSolicitudUseCase

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
}
