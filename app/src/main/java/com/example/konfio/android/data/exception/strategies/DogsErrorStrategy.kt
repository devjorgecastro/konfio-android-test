package com.example.konfio.android.data.exception.strategies

import android.database.sqlite.SQLiteAccessPermException
import com.example.konfio.android.domain.exception.DomainException
import com.example.konfio.android.domain.exception.GeneralDatabaseException
import javax.inject.Inject

class DogsErrorStrategy @Inject constructor(): DataSideEffectStrategy {

    override val managedExceptions = listOf<Class<out Throwable>>(
        SQLiteAccessPermException::class.java
    )

    override fun getDomainException(error: Throwable, data: Any?): DomainException {
        return if (hasManagedError(error)) {
            GeneralDatabaseException(error.message.orEmpty(), data)
        } else {
            DomainException(error.message.orEmpty())
        }
    }

    override fun hasManagedError(error: Throwable): Boolean {
        return managedExceptions.contains(error::class.java)
    }
}