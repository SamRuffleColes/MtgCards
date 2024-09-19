package es.rufflecol.sam.mtgcards.presentation.carddetail

import androidx.lifecycle.SavedStateHandle
import es.rufflecol.sam.mtgcards.MainDispatcherRule
import es.rufflecol.sam.mtgcards.domain.model.DetailedMtgCard
import es.rufflecol.sam.mtgcards.domain.usecase.GetMtgCardDetailUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CardDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CardDetailViewModel
    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
    private val getCardDetail = mockk<GetMtgCardDetailUseCase>()

    @Test
    fun `init should call getCardDetail with correct card ID`() = runTest {
        // Given
        every { savedStateHandle.get<String>("cardId") } returns ID

        // When
        viewModel = CardDetailViewModel(savedStateHandle, getCardDetail)

        // Then
        coVerify { getCardDetail(ID) }
    }

    @Test
    fun `given null id, init should not call getCardDetail`() = runTest {
        // Given
        every { savedStateHandle.get<String>("cardId") } returns null

        // When
        viewModel = CardDetailViewModel(savedStateHandle, getCardDetail)

        // Then
        coVerify(exactly = 0) { getCardDetail(any()) }
    }

    @Test
    fun `init should update viewState with data on success`() = runTest {
        // Given
        every { savedStateHandle.get<String>("cardId") } returns ID
        val detailedCard = DetailedMtgCard.Empty.copy(id = ID)
        coEvery { getCardDetail(ID) } returns flowOf(detailedCard)

        // When
        viewModel = CardDetailViewModel(savedStateHandle, getCardDetail)

        // Then
        assertEquals(
            CardDetailViewModel.ViewState.Data(detailedCard),
            viewModel.viewState.value
        )
    }

    @Test
    fun `init should update viewState with error on failure`() = runTest {
        // Given
        every { savedStateHandle.get<String>("cardId") } returns ID
        coEvery { getCardDetail(any()) } throws Exception("Test exception")

        // When
        viewModel = CardDetailViewModel(savedStateHandle, getCardDetail)

        // Then
        assertTrue(viewModel.viewState.value is CardDetailViewModel.ViewState.Error)
    }

    companion object {
        const val ID = "test_card_id"
    }
}
