package es.rufflecol.sam.mtgcards.domain.usecase

import es.rufflecol.sam.mtgcards.domain.mapper.MtgCardMapper
import es.rufflecol.sam.mtgcards.domain.model.DetailedMtgCard
import es.rufflecol.sam.mtgcards.domain.repository.MtgCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMtgCardDetailUseCase @Inject constructor(
    private val repository: MtgCardRepository,
    private val mapper: MtgCardMapper,
) {

    suspend operator fun invoke(id: String): Flow<DetailedMtgCard> {
        return repository.getMtgCardDetail(id).map { dto ->
            mapper.mapDetailed(dto)
        }
    }

}