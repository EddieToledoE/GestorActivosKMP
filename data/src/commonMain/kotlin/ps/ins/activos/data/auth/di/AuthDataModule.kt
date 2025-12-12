package ps.ins.activos.data.auth.di

import org.koin.dsl.module
import ps.ins.activos.data.auth.networking.RemoteLoginDataSource
import ps.ins.activos.data.core.networking.HttpClientFactory
import ps.ins.activos.data.core.networking.createHttpEngine
import ps.ins.activos.domain.auth.datasource.LoginDataSource

val authDataModule = module {
    // HttpClient singleton para networking
    single { HttpClientFactory.create(createHttpEngine()) }
    
    // LoginDataSource implementation
    single<LoginDataSource> { 
        RemoteLoginDataSource(
            httpClient = get(),
            permissionManager = get()
        )
    }
}
