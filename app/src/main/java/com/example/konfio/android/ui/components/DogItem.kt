@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.konfio.android.ui.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.theme.DogsTheme

private const val IMAGE_HEIGHT = 220
private const val CARD_HEIGHT_RATIO = 0.85f
private const val IMAGE_WIDTH = 160
private const val DEFAULT_CORNER_RADIUS = 12

@Composable
fun SharedTransitionScope.DogItem(
    dog: Dog,
    onDogClick: (Dog) -> Unit,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope? = null
) {

    val configuration = LocalConfiguration.current.orientation
    when (configuration) {
        Configuration.ORIENTATION_PORTRAIT -> {
            PortraitContent(
                dog = dog,
                modifier = modifier,
                onDogClick = onDogClick
            )
        }
        else -> {
            LandscapeContent(
                dog = dog,
                animatedVisibilityScope = animatedVisibilityScope,
                modifier = modifier,
                onDogClick = onDogClick
            )
        }
    }
}

@Composable
private fun SharedTransitionScope.LandscapeContent(
    dog: Dog,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onDogClick: (Dog) -> Unit
) {
    DogImage(
        dog = dog,
        modifier = Modifier
            .clickable(onClick = { onDogClick(dog) })
            .width(IMAGE_WIDTH.dp)
            .height(IMAGE_HEIGHT.dp)
            .clip(RoundedCornerShape(DEFAULT_CORNER_RADIUS.dp))
            .then(
                if (animatedVisibilityScope != null) {
                    Modifier.sharedElement(
                        sharedContentState = rememberSharedContentState(key = dog.dogName),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                } else {
                    modifier
                }
            )
    )
}

@Composable
private fun PortraitContent(
    dog: Dog,
    modifier: Modifier = Modifier,
    onDogClick: (Dog) -> Unit,
) {
    Row(
        modifier = modifier.clickable { onDogClick(dog) },
        verticalAlignment = Alignment.Bottom
    ) {

        DogImage(
            dog = dog,
            modifier = Modifier
                .width(IMAGE_WIDTH.dp)
                .height(IMAGE_HEIGHT.dp)
                .clip(RoundedCornerShape(DEFAULT_CORNER_RADIUS.dp))
        )

        DogInfo(
            dog = dog,
            modifier = Modifier
                .height(IMAGE_HEIGHT.dp * CARD_HEIGHT_RATIO)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topEnd = DEFAULT_CORNER_RADIUS.dp,
                        bottomEnd = DEFAULT_CORNER_RADIUS.dp
                    )
                )

        )
    }
}

@Preview(showBackground = true)
@Composable
fun DogItemPreview() {
    DogsTheme {
        SharedTransitionLayout {
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
}
