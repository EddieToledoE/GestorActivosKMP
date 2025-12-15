package ps.ins.activos.data.usuario.di

import org.koin.dsl.module
import ps.ins.activos.data.usuario.networking.KtorRemoteUsuarioDataSource
import ps.ins.activos.data.usuario.networking.RemoteUsuarioDataSource
import ps.ins.activos.data.usuario.repository.UsuarioRepositoryImpl
import ps.ins.activos.domain.usuario.repository.UsuarioRepository
import ps.ins.activos.domain.usuario.usecase.GetUsuarioByFortiaUseCase

val usuarioDataModule = module {
    single<RemoteUsuarioDataSource> {
        KtorRemoteUsuarioDataSource(
            httpClient = get(),
            settings = get()
        )
    }

    single<UsuarioRepository> {
        UsuarioRepositoryImpl(
            remoteDataSource = get()
        )
    }

    factory { GetUsuarioByFortiaUseCase(get()) }
}
