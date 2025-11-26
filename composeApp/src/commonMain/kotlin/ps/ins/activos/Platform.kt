package ps.ins.activos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform