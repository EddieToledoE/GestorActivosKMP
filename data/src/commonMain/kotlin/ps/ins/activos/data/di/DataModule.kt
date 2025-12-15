package ps.ins.activos.data.di


import com.russhwolf.settings.Settings
import org.koin.dsl.bind
import org.koin.dsl.module
import ps.ins.activos.data.auth.di.authDataModule
import ps.ins.activos.data.activos.di.activosDataModule
import ps.ins.activos.data.permissions.PermissionManagerImpl
import ps.ins.activos.domain.permissions.PermissionManager
import ps.ins.activos.data.usuario.di.usuarioDataModule

val dataModule = module {
    includes(authDataModule, activosDataModule, usuarioDataModule)

    single { Settings() }
    single { PermissionManagerImpl(get()) } bind PermissionManager::class
}