package ps.ins.activos.data.activos.di

import org.koin.dsl.module
import ps.ins.activos.data.activos.networking.KtorRemoteActivosDataSource
import ps.ins.activos.data.activos.networking.RemoteActivosDataSource
import ps.ins.activos.data.activos.repository.ActivosRepositoryImpl
import ps.ins.activos.domain.activos.repository.ActivosRepository

val activosDataModule = module {
    single<RemoteActivosDataSource> {
        KtorRemoteActivosDataSource(
            httpClient = get(),
            settings = get()
        )
    }
    
    single<ActivosRepository> {
        ActivosRepositoryImpl(
            remoteDataSource = get()
        )
    }
}
