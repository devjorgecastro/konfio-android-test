package com.example.konfio.android.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> CollectEffect(
    effect: Flow<T>,
    block: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(effect) {
        effect.collectLatest {
            block(it)
        }
    }
}
