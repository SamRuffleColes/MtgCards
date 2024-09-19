package es.rufflecol.sam.mtgcards.presentation.carddetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.rufflecol.sam.mtgcards.domain.model.DetailedMtgCard
import es.rufflecol.sam.mtgcards.domain.usecase.GetMtgCardDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCardDetail: GetMtgCardDetailUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Data(DetailedMtgCard.Empty))
    val viewState = _viewState.asStateFlow()

    init {
        savedStateHandle.get<String>(CARD_ID_SAVED_STATE_KEY)?.let {
            loadData(it)
        }
    }

    private fun loadData(id: String) = viewModelScope.launch {
        try {
            getCardDetail(id).collect { detailedCard ->
                _viewState.value = ViewState.Data(detailedCard)
            }
        } catch (e: Exception) {
            _viewState.value = ViewState.Error
        }
    }

    sealed class ViewState {
        data object Error : ViewState()
        data class Data(val card: DetailedMtgCard) : ViewState()
    }

    companion object {
        private const val CARD_ID_SAVED_STATE_KEY = "cardId"
    }
}