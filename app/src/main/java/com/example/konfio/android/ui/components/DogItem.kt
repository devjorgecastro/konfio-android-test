package com.example.konfio.android.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.konfio.android.R
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.theme.DogsTheme
import com.example.konfio.android.ui.theme.description_color
import com.example.konfio.android.ui.theme.title_color

private const val IMAGE_HEIGHT = 220
private const val IMAGE_WIDTH = 160
private const val DEFAULT_CORNER_RADIUS = 12
private const val CARD_HEIGHT_RATIO = 0.85f
private val CARD_VERTICAL_SPACING = 8.dp
private val CARD_PADDING = PaddingValues(start = 20.dp, top = 20.dp, end = 8.dp, bottom = 8.dp)

@Composable
fun DogItem(
    dog: Dog,
    onDogClick: (Dog) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable { onDogClick(dog) },
        verticalAlignment = Alignment.Bottom
    ) {

        DogImage(dog)

        Column(
            verticalArrangement = Arrangement.spacedBy(CARD_VERTICAL_SPACING),
            modifier = Modifier
                .height(IMAGE_HEIGHT.dp * CARD_HEIGHT_RATIO)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topEnd = DEFAULT_CORNER_RADIUS.dp,
                        bottomEnd = DEFAULT_CORNER_RADIUS.dp
                    )
                )
                .background(Color.White)
                .padding(CARD_PADDING)
        ) {
            Text(
                text = dog.dogName,
                style = MaterialTheme.typography.titleLarge,
                color = title_color
            )
            Text(
                text = dog.description,
                style = MaterialTheme.typography.bodyMedium,
                color = description_color
            )
            Text(
                text = stringResource(R.string.almost_n_years_old, dog.age),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun DogImage(dog: Dog) {
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
        modifier = Modifier
            .width(IMAGE_WIDTH.dp)
            .height(IMAGE_HEIGHT.dp)
            .clip(RoundedCornerShape(DEFAULT_CORNER_RADIUS.dp)),
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        error = painterResource(id = R.drawable.ic_launcher_background)
    )
}

@Preview(showBackground = true)
@Composable
fun DogItemPreview() {
    DogsTheme {
        DogItem(
            dog = Dog(
                dogName = "Rex",
                description = "A friendly dog",
                age = 5,
                image = "https://example.com/dog.jpg"
            ),
            onDogClick = {}
        )
    }
}
