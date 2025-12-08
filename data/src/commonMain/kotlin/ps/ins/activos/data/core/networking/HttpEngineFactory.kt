package ps.ins.activos.data.core.networking

import io.ktor.client.engine.HttpClientEngine

expect fun createHttpEngine(): HttpClientEngine
