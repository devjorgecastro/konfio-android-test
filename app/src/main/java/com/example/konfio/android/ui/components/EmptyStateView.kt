package com.example.konfio.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.konfio.android.R

private const val EmptyStateIconSize = 80
private const val EmptyStateSpacing = 8

@Composable
fun EmptyStateView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(EmptyStateSpacing.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.empty_state),
                contentDescription = null,
                modifier = Modifier
                    .size(EmptyStateIconSize.dp)
            )
            Text(stringResource(R.string.no_results_found))
        }
    }
}
