package com.example.konfio.android.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.konfio.android.R
import com.example.konfio.android.ui.components.DogItem
import com.example.konfio.android.ui.theme.DogsTheme
import kotlinx.coroutines.delay

private data class DogExample(
    val dogName: String,
    val description: String,
    val age: Int,
    val image: String
)

private fun getExampleDogs(): List<DogExample> = listOf(
    DogExample(
        dogName = "Rex",
        description = "He is much more passive and is the first to suggest to rescue and not eat The Little Pilot",
        age = 5,
        image = "https://static.wikia.nocookie.net/isle-of-dogs/images/a/af/Rex.jpg/revision/latest/scale-to-width-down/666?cb=20180625001634"
    ),
    DogExample(
        dogName = "Spots",
        description = "Is the brother of Chief and are also a former guard dog for Mayor Kobayashi's ward",
        age = 3,
        image = "https://static.wikia.nocookie.net/isle-of-dogs/images/6/6b/Spots.jpg/revision/latest/scale-to-width-down/666?cb=20180624191101"
    ),
    DogExample(
        dogName = "Chief",
        description = "He is a leader of a pack of dogs",
        age = 8,
        image = "https://static.wikia.nocookie.net/isle-of-dogs/images/1/1d/Chief-0.jpg/revision/latest/scale-to-width-down/666?cb=20180624184431"
    ),
    DogExample(
        dogName = "Boss",
        description = "Little is known about Boss' origins other than he was the mascot for the Megasaki Dragons",
        age = 4,
        image = "https://static.wikia.nocookie.net/isle-of-dogs/images/6/6f/Boss.jpg/revision/latest/scale-to-width-down/666?cb=20180624190948"
    ),
    DogExample(
        dogName = "Spots",
        description = "Is the brother of Chief and are also a former guard dog for Mayor Kobayashi's ward",
        age = 2,
        image = "https://static.wikia.nocookie.net/isle-of-dogs/images/6/6b/Spots.jpg/revision/latest/scale-to-width-down/666?cb=20180624191101"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsScreen(
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    val dogs = remember { getExampleDogs() }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dogs) { dog ->
                    DogItem(
                        name = dog.dogName,
                        imageUrl = dog.image,
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(2000)
        isLoading = false
    }
}

@Preview(showBackground = true)
@Composable
fun DogsScreenPreview() {
    DogsTheme {
        DogsScreen()
    }
} 