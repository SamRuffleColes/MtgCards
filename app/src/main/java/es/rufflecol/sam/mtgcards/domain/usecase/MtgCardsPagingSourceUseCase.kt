package es.rufflecol.sam.mtgcards.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import es.rufflecol.sam.mtgcards.domain.mapper.MtgCardMapper
import es.rufflecol.sam.mtgcards.domain.model.SimpleMtgCard
import es.rufflecol.sam.mtgcards.domain.repository.MtgCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveMtgCardsUseCase @Inject constructor(
    private val repository: MtgCardRepository,
    private val mapper: MtgCardMapper,
) {

    suspend operator fun invoke(): Flow<PagingData<SimpleMtgCard>> {
        return repository.getMtgCardsFlow().map {
            it.filter { dto ->
                // skip dtos missing key data
                !dto.id.isNullOrBlank() || !dto.name.isNullOrBlank() || !dto.imageUrl.isNullOrBlank()
            }.map { dto ->
                mapper.mapSimple(dto)
            }
        }
    }
}