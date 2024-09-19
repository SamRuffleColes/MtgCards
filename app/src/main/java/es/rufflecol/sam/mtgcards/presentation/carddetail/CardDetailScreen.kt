package es.rufflecol.sam.mtgcards.presentation.carddetail

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import es.rufflecol.sam.mtgcards.R
import es.rufflecol.sam.mtgcards.domain.model.DetailedMtgCard
import es.rufflecol.sam.mtgcards.ui.theme.Typography

@Composable
fun CardDetailScreen(viewModel: CardDetailViewModel = hiltViewModel()) {
    val state = viewModel.viewState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when (val viewState = state.value) {
            CardDetailViewModel.ViewState.Error ->
                Text(
                    modifier = Modifier.padding(innerPadding),
                    text = stringResource(id = R.string.error),
                )

            is CardDetailViewModel.ViewState.Data -> CardDetail(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                card = viewState.card,
            )

        }
    }
}

@Composable
private fun CardDetail(modifier: Modifier = Modifier, card: DetailedMtgCard) {
    val scrollState = rememberScrollState()
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .scrollable(scrollState, orientation = Orientation.Vertical)
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = card.name,
                style = Typography.titleMedium,
                color = Color.Black,
            )
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                contentScale = ContentScale.Inside,
                model = card.imageUrl,
                placeholder = painterResource(id = R.drawable.placeholder),
                contentDescription = stringResource(id = R.string.cd_card_image, card.name),
            )
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                text = card.manaCost,
                style = Typography.titleSmall,
                color = Color.Black,
            )
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                text = card.powerAndToughness,
                style = Typography.titleSmall,
                color = Color.Black,
            )
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                text = card.artist,
                style = Typography.titleSmall,
                color = Color.Black,
            )
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                text = card.type,
                style = Typography.titleSmall,
                color = Color.Black,
            )
        }
    }
}