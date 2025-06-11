package com.example.konfio.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.konfio.android.R
import com.example.konfio.android.ui.screens.dogs.DogsEvent

private const val SnackbarPadding = 16
private const val SnackbarIconSize = 40
private const val SnackbarIconTextSpacing = 8

@Composable
fun BoxScope.SnackbarView(
    description: String = "",
    onDispatchEvent: (DogsEvent) -> Unit = {}
) {
    Snackbar(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(SnackbarPadding.dp),
        action = {
            TextButton(
                onClick = { onDispatchEvent(DogsEvent.DismissError) }
            ) {
                Text(stringResource(R.string.close))
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SnackbarIconTextSpacing.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_signal_disconnected_24),
                contentDescription = null,
                modifier = Modifier
                    .size(SnackbarIconSize.dp)
            )
            Text(description)
        }
    }
}
