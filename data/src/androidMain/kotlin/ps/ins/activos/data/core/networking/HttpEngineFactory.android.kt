package ps.ins.activos.data.core.networking

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun createHttpEngine(): HttpClientEngine = OkHttp.create {
    config {
        retryOnConnectionFailure(false)
        connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
    }
}
