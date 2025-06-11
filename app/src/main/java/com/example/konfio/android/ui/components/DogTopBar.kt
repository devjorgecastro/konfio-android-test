package com.example.konfio.android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.konfio.android.R
import com.example.konfio.android.ui.screens.dogs.DogsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogTopBar(
    onDispatchEvent: (DogsEvent) -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.dogs_we_love),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        actions = {
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                RefreshIcon(onDispatchEvent = onDispatchEvent)
            }
        }
    )
}

@Composable
private fun RefreshIcon(
    onDispatchEvent: (DogsEvent) -> Unit = {}
) {
    IconButton(onClick = { onDispatchEvent(DogsEvent.RefreshDogs) }) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = null
        )
    }
}
