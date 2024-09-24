package es.rufflecol.sam.mtgcards.presentation.cardlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import es.rufflecol.sam.mtgcards.domain.model.SimpleMtgCard
import es.rufflecol.sam.mtgcards.domain.usecase.ObserveMtgCardsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val observeCards: ObserveMtgCardsUseCase,
) : ViewModel() {

    private val _cardsPagingData = MutableStateFlow<PagingData<SimpleMtgCard>?>(null)

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Data(_cardsPagingData.filterNotNull()))
    val viewState = _viewState.asStateFlow()

    private val _viewIntents = Channel<ViewIntent>(Channel.BUFFERED)

    private val _viewEvents = Channel<ViewEvent?>(Channel.BUFFERED)
    val viewEvents = _viewEvents.receiveAsFlow()

    init {
        refreshData()
        viewModelScope.launch {
            _viewIntents.consumeEach { intent ->
                processIntent(intent)
            }
        }
    }

    fun applyAction(action: ViewIntent) = viewModelScope.launch {
        _viewIntents.send(action)
    }

    private fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnCardClick -> onCardClick(intent.mtgCard)
            ViewIntent.RefreshData -> refreshData()
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            try {
                _cardsPagingData.value = observeCards().cachedIn(viewModelScope).first()
                _viewState.value = ViewState.Data(cardsPagingData = _cardsPagingData.filterNotNull())
            } catch (e: Exception) {
                _viewState.value = ViewState.Error
            }
        }
    }

    private fun onCardClick(mtgCard: SimpleMtgCard) = viewModelScope.launch {
        _viewEvents.send(ViewEvent.NavigateToDetail(mtgCard.id))
    }

    override fun onCleared() {
        _viewIntents.close()
        _viewEvents.close()
        super.onCleared()
    }

    sealed class ViewIntent {
        data object RefreshData : ViewIntent()
        data class OnCardClick(val mtgCard: SimpleMtgCard) : ViewIntent()
    }

    sealed class ViewEvent {
        data class NavigateToDetail(val mtgCardId: String) : ViewEvent()
    }

    sealed class ViewState {
        data object Error : ViewState()
        data class Data(val cardsPagingData: Flow<PagingData<SimpleMtgCard>>) : ViewState()
    }
}