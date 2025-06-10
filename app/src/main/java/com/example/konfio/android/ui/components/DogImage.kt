package com.example.konfio.android.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.konfio.android.R
import com.example.konfio.android.domain.model.Dog

@Composable
fun DogImage(
    dog: Dog,
    modifier: Modifier = Modifier

) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(dog.image)
            .crossfade(true)
            .listener(
                onError = { request, errorResult ->
                    Log.e("DogItem", "Error al cargar imagen", errorResult.throwable.cause)
                }
            )
            .build(),
        contentDescription = dog.dogName,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        error = painterResource(id = R.drawable.ic_launcher_background)
    )
}
