package es.rufflecol.sam.mtgcards.domain.repository

import androidx.paging.PagingData
import es.rufflecol.sam.mtgcards.data.remote.dto.MtgCardDto
import kotlinx.coroutines.flow.Flow

interface MtgCardRepository {
    suspend fun getMtgCardsFlow(): Flow<PagingData<MtgCardDto>>
    suspend fun getMtgCardDetail(id: String): Flow<MtgCardDto>
}