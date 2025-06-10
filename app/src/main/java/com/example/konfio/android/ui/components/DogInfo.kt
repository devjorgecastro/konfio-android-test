package com.example.konfio.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.konfio.android.R
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.theme.description_color
import com.example.konfio.android.ui.theme.title_color

private val CARD_VERTICAL_SPACING = 8.dp
private val CARD_PADDING = PaddingValues(start = 20.dp, top = 20.dp, end = 8.dp, bottom = 8.dp)

@Composable
fun DogInfo(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(CARD_VERTICAL_SPACING),
        modifier = modifier
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
