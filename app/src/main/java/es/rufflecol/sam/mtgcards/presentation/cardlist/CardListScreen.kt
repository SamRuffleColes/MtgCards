package es.rufflecol.sam.mtgcards.presentation.cardlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import es.rufflecol.sam.mtgcards.R
import es.rufflecol.sam.mtgcards.domain.model.SimpleMtgCard
import es.rufflecol.sam.mtgcards.presentation.common.RetryableError
import es.rufflecol.sam.mtgcards.ui.theme.Typography
import kotlinx.coroutines.flow.Flow

@Composable
fun CardsListScreen(viewModel: CardListViewModel = hiltViewModel(), onNavigateToCardDetail: (String) -> Unit) {
    CardListEventHandler(
        eventsFlow = viewModel.viewEvents,
        onNavigateToCardDetail = onNavigateToCardDetail,
    )

    val state = viewModel.viewState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when (val viewState = state.value) {
            CardListViewModel.ViewState.Error ->
                RetryableError(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onRetryClick = { viewModel.applyAction(CardListViewModel.ViewIntent.RefreshData) },
                )

            is CardListViewModel.ViewState.Data ->
                CardsList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    cards = viewState.cardsPagingData,
                    onPullToRefresh = { viewModel.applyAction(CardListViewModel.ViewIntent.RefreshData) },
                    onCardClick = { card -> viewModel.applyAction(CardListViewModel.ViewIntent.OnCardClick(card)) },
                )
        }
    }
}

@Composable
fun CardListEventHandler(eventsFlow: Flow<CardListViewModel.ViewEvent?>, onNavigateToCardDetail: (String) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        eventsFlow.collect { event ->
            when (event) {
                is CardListViewModel.ViewEvent.NavigateToDetail -> onNavigateToCardDetail(event.mtgCardId)
                null -> {
                    // do nothing
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardsList(
    modifier: Modifier,
    cards: Flow<PagingData<SimpleMtgCard>>,
    onPullToRefresh: () -> Unit,
    onCardClick: (SimpleMtgCard) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        onPullToRefresh()
    }

    val pagingItems: LazyPagingItems<SimpleMtgCard> = cards.collectAsLazyPagingItems()

    LaunchedEffect(pagingItems.loadState) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> Unit
            is LoadState.Error, is LoadState.NotLoading -> {
                pullToRefreshState.endRefresh()
            }
        }
    }

    Box(
        modifier = modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            items(count = pagingItems.itemCount) { index ->
                val card = pagingItems[index] ?: return@items
                CardItem(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(all = dimensionResource(id = R.dimen.padding_medium)),
                    card = card,
                    onCardClick = onCardClick,
                )
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState
        )
    }
}

@Composable
private fun CardItem(modifier: Modifier = Modifier, card: SimpleMtgCard, onCardClick: (SimpleMtgCard) -> Unit) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(all = dimensionResource(id = R.dimen.padding_medium))
                .clickable { onCardClick(card) },
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
                    .weight(1f)
                    .padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                contentScale = ContentScale.Inside,
                model = card.imageUrl,
                placeholder = painterResource(id = R.drawable.placeholder),
                contentDescription = stringResource(id = R.string.cd_card_image, card.name),
            )
            Row {
                Text(
                    text = card.manaCost,
                    style = Typography.titleSmall,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = card.powerAndToughness,
                    style = Typography.titleSmall,
                    color = Color.Black,
                )
            }
        }
    }
}