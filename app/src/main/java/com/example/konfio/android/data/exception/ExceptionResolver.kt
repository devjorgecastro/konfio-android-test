package com.example.konfio.android.data.exception

import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.example.konfio.android.data.exception.strategies.DataSideEffectStrategy
import com.example.konfio.android.domain.exception.DiskReadWriteException
import com.example.konfio.android.domain.exception.DomainException
import com.example.konfio.android.domain.exception.GeneralDatabaseException
import com.example.konfio.android.domain.exception.NetworkTimeoutException
import com.example.konfio.android.domain.exception.NoInternetConnectionException
import com.example.konfio.android.domain.exception.NoNetworkProviderException
import com.example.konfio.android.domain.exception.SSLValidationException
import com.example.konfio.android.domain.exception.StorageLimitExceededException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class ExceptionResolver {
    fun get(error: Throwable, strategy: DataSideEffectStrategy, data: Any? = null): DomainException {
        return if (strategy.hasManagedError(error)) {
            strategy.getDomainException(error, data)
        } else {
            processError(error)
        }
    }

    private fun processError(error: Throwable) = when (error) {
        is UnknownHostException -> NoInternetConnectionException(error.message.orEmpty())
        is SocketTimeoutException -> NetworkTimeoutException(error.message.orEmpty())
        is ConnectException -> NoNetworkProviderException(error.message.orEmpty())
        is SSLHandshakeException -> SSLValidationException(error.message.orEmpty())
        is SQLiteFullException -> StorageLimitExceededException(error.message.orEmpty())
        is SQLiteDiskIOException -> DiskReadWriteException(error.message.orEmpty())
        is SQLiteException -> GeneralDatabaseException(error.message.orEmpty())
        else -> DomainException(error.message.orEmpty())
    }
}
