package ps.ins.activos.domain.auth.datasource
import ps.ins.activos.domain.auth.entities.LoginUser
import ps.ins.activos.domain.auth.entities.User
import ps.ins.activos.domain.core.util.NetworkError
import ps.ins.activos.domain.core.util.Result

interface LoginDataSource {
    suspend fun doLogin(user: LoginUser): Result<User, NetworkError>
}

