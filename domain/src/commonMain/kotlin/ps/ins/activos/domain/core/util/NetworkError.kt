package ps.ins.activos.domain.core.util

enum class NetworkError : Error {
    BAD_REQUEST, // 400
    INTERNAL_SERVER_ERROR, // 500
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN,
    INCORRECT_CREDENTIALS
}