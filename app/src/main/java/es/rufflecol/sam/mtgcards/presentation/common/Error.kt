package es.rufflecol.sam.mtgcards.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import es.rufflecol.sam.mtgcards.R
import es.rufflecol.sam.mtgcards.ui.theme.Typography

@Composable
fun RetryableError(modifier: Modifier = Modifier, onRetryClick: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            text = stringResource(id = R.string.error),
            color = Color.Red,
            style = Typography.titleLarge
        )
        Button(onClick = onRetryClick) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}