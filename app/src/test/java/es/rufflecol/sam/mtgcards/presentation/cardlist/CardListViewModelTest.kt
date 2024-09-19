package es.rufflecol.sam.mtgcards.presentation.cardlist

import app.cash.turbine.test
import es.rufflecol.sam.mtgcards.MainDispatcherRule
import es.rufflecol.sam.mtgcards.domain.model.SimpleMtgCard
import es.rufflecol.sam.mtgcards.domain.usecase.ObserveMtgCardsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CardListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val observeMtgCardsUseCase: ObserveMtgCardsUseCase = mockk(relaxed = true)

    private lateinit var viewModel: CardListViewModel

    // TODO: SRC - 19/09/24 verify PagingData is correct
    @Test
    fun `given ObserveMtgCardsUseCase success, init should update ViewState to Data`() = runTest {
        coEvery { observeMtgCardsUseCase() } returns mockk(relaxed = true)

        viewModel = CardListViewModel(observeMtgCardsUseCase)

        advanceUntilIdle()

        assert(viewModel.viewState.value is CardListViewModel.ViewState.Data)
    }

    @Test
    fun `given ObserveMtgCardsUseCase throws exception, init should update ViewState to Error`() = runTest {
        coEvery { observeMtgCardsUseCase() } throws Exception()

        viewModel = CardListViewModel(observeMtgCardsUseCase)

        assert(viewModel.viewState.value is CardListViewModel.ViewState.Error)
    }

    // TODO: SRC - 19/09/24 verify PagingData is correct
    @Test
    fun `given ObserveMtgCardsUseCase success, RefreshData intent should update ViewState to Data`() = runTest {
        coEvery { observeMtgCardsUseCase() } returns mockk(relaxed = true)
        viewModel = CardListViewModel(observeMtgCardsUseCase)

        viewModel.applyAction(CardListViewModel.ViewIntent.RefreshData)

        assert(viewModel.viewState.value is CardListViewModel.ViewState.Data)
    }

    @Test
    fun `given ObserveMtgCardsUseCase throws exception, RefreshData intent should update ViewState to Error`() = runTest {
        // first return mock
        coEvery { observeMtgCardsUseCase() } returns mockk(relaxed = true)
        viewModel = CardListViewModel(observeMtgCardsUseCase)
        // next throw exception so it is thrown on RefreshData intent
        coEvery { observeMtgCardsUseCase() } throws Exception()

        viewModel.applyAction(CardListViewModel.ViewIntent.RefreshData)

        assert(viewModel.viewState.value is CardListViewModel.ViewState.Error)
    }

    @Test
    fun `OnCardClick intent should send NavigateToDetail event`() = runTest {
        val cardId = "1"
        viewModel = CardListViewModel(observeMtgCardsUseCase)

        viewModel.applyAction(CardListViewModel.ViewIntent.OnCardClick(SimpleMtgCard.Empty.copy(id = cardId, name = "Card One")))

        viewModel.viewEvents.test {
            assertEquals(CardListViewModel.ViewEvent.NavigateToDetail("1"), awaitItem())
        }
    }

}