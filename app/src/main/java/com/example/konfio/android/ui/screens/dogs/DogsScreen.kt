@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.example.konfio.android.ui.screens.dogs

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.konfio.android.R
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.components.AnimatedDogDetailComponent
import com.example.konfio.android.ui.components.DogItem
import com.example.konfio.android.ui.theme.DogsTheme

@Composable
fun SharedTransitionScope.DogsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: DogsViewModel = hiltViewModel(),
    onItemClick: (Dog) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    DogsScreenContent(
        state = state,
        animatedVisibilityScope = animatedVisibilityScope,
        onDispatchEvent = viewModel::onEvent,
        onItemClick = onItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SharedTransitionScope.DogsScreenContent(
    state: DogsState = DogsState(),
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onDispatchEvent: (DogsEvent) -> Unit = {},
    onItemClick: (Dog) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                DogTopBar(onDispatchEvent = onDispatchEvent)
            }
        ) { paddingValues ->
            val pullToRefreshState = rememberPullToRefreshState()
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                state = pullToRefreshState,
                onRefresh = { onDispatchEvent(DogsEvent.RefreshDogs) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = state.isRefreshing,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        state = pullToRefreshState
                    )
                }
            ) {
                DogsContent(
                    state = state,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onDispatchEvent = onDispatchEvent,
                    onItemClick = onItemClick
                )
            }
        }

        state.selectedDog?.let { dog ->
            AnimatedDogDetailComponent(
                dog = dog,
                onDismiss = { onDispatchEvent(DogsEvent.SelectDog(null)) }
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (state.showEmptyState) {
            EmptyStateView()
        }

        state.errorMessageRes?.let { error ->
            SnackbarView(
                description = stringResource(error),
                onDispatchEvent = onDispatchEvent
            )
        }
    }
}

@Composable
private fun DogTopBar(
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

@Composable
private fun SharedTransitionScope.DogsContent(
    state: DogsState = DogsState(),
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onDispatchEvent: (DogsEvent) -> Unit = {},
    onItemClick: (Dog) -> Unit = {}
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                items(state.dogs) { dog ->
                    DogItem(
                        dog = dog,
                        onDogClick = { onDispatchEvent(DogsEvent.SelectDog(dog)) }
                    )
                }
            }
        }
        else -> {
            LazyRow(
                horizontalArrangement = if (state.dogs.size <= 4) {
                    Arrangement.SpaceBetween
                } else {
                    Arrangement.spacedBy(16.dp)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                items(state.dogs) { dog ->
                    DogItem(
                        dog = dog,
                        animatedVisibilityScope = animatedVisibilityScope,
                        onDogClick = { onItemClick(dog) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BoxScope.SnackbarView(
    description: String = "",
    onDispatchEvent: (DogsEvent) -> Unit = {}
) {
    Snackbar(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(16.dp),
        action = {
            TextButton(
                onClick = { onDispatchEvent(DogsEvent.DismissError) }
            ) {
                Text(stringResource(R.string.close))
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_signal_disconnected_24),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
            Text(description)
        }
    }
}

@Composable
private fun EmptyStateView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.empty_state),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
            Text(stringResource(R.string.no_results_found))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStateViewPreview() {
    DogsTheme {
        EmptyStateView()
    }
}

@Preview
@Composable
private fun DogsScreenPreview() {
    DogsTheme {
        SharedTransitionLayout {
            DogsScreenContent(
                state = DogsState(
                    dogs = listOf(
                        Dog(
                            dogName = "Fox",
                            description = "Fox description",
                            age = 3,
                            image = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
                        ),
                        Dog(
                            dogName = "Pepito",
                            description = "Pepito description",
                            age = 4,
                            image = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
                        )
                    )
                )
            )
        }
    }
}