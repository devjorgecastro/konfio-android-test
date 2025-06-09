package com.example.konfio.android.data.repository

import com.example.konfio.android.data.exception.strategies.DataSideEffectStrategy
import com.example.konfio.android.data.exception.ExceptionResolver
import com.example.konfio.android.data.exception.strategies.DefaultErrorStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

open class BaseRepository {
    suspend fun<T> execute(
        context: CoroutineContext = Dispatchers.IO,
        dataErrorStrategy: DataSideEffectStrategy = DefaultErrorStrategy(),
        strategyDataBlock: (suspend() -> T)? = null,
        block: suspend() -> T,
    ): T {
        val handleException by lazy { ExceptionResolver() }
        return withContext(context){
            try {
                block()
            }
            catch (error: Throwable) {
                val data = strategyDataBlock?.invoke()
                throw handleException.get(error, dataErrorStrategy, data)
            }
        }
    }

    fun <T> executeFlow(
        context: CoroutineContext = Dispatchers.IO,
        dataErrorStrategy: DataSideEffectStrategy = DefaultErrorStrategy(),
        strategyDataBlock: (suspend () -> T)? = null,
        block: () -> Flow<T>
    ): Flow<T> {
        val handleException by lazy { ExceptionResolver() }
        return block()
            .catch { error ->
                val data = strategyDataBlock?.invoke()
                throw handleException.get(error, dataErrorStrategy, data)
            }
    }
}
