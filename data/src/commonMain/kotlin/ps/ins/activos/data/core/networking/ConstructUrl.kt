package ps.ins.activos.data.core.networking
import ps.ins.activos.config.BuildKonfig


fun constructUrl(endpoint: String): String {
    val base = BuildKonfig.API_URL.trimEnd('/')
    val path = endpoint.trimStart('/')
    return "$base/$path"
}
