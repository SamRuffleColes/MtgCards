package es.rufflecol.sam.mtgcards.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import es.rufflecol.sam.mtgcards.data.remote.MtgService
import es.rufflecol.sam.mtgcards.data.remote.dto.MtgCardDto
import es.rufflecol.sam.mtgcards.domain.repository.MtgCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MtgCardRepositoryImpl @Inject constructor(
    private val service: MtgService,
) : MtgCardRepository {

    override suspend fun getMtgCardsFlow(): Flow<PagingData<MtgCardDto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = MtgCardsPagingDataSource.PAGING_SIZE),
            pagingSourceFactory = { MtgCardsPagingDataSource(service) }
        ).flow
    }

    override suspend fun getMtgCardDetail(id: String): Flow<MtgCardDto> {
        return flowOf(service.fetchCard(id).card)
    }

}