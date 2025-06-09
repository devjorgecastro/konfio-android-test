package com.example.konfio.android.domain.exception

open class DomainException(override val message: String = String()): Exception(message) {
    fun isNetworkError(): Boolean {
        return this is NoInternetConnectionException ||
                this is NoNetworkProviderException ||
                this is NetworkTimeoutException
    }
}

data class StorageLimitExceededException(override val message: String): DomainException()
data class DiskReadWriteException(override val message: String): DomainException()
data class NoInternetConnectionException(override val message: String): DomainException()
data class NoNetworkProviderException(override val message: String): DomainException()
data class SSLValidationException(override val message: String): DomainException()
data class NetworkTimeoutException(override val message: String): DomainException()

data class GeneralDatabaseException(
    override val message: String,
    val localData: Any? = null
): DomainException()
