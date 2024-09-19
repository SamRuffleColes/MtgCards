package es.rufflecol.sam.mtgcards.domain.mapper

import es.rufflecol.sam.mtgcards.data.remote.dto.MtgCardDto
import es.rufflecol.sam.mtgcards.domain.model.DetailedMtgCard
import es.rufflecol.sam.mtgcards.domain.model.SimpleMtgCard
import javax.inject.Inject

class MtgCardMapper @Inject constructor() {

    fun mapSimple(dto: MtgCardDto): SimpleMtgCard {
        return SimpleMtgCard(
            id = dto.id.orEmpty(),
            name = dto.name.orEmpty(),
            imageUrl = mapImageUrl(dto.imageUrl.orEmpty()),
            manaCost = dto.manaCost.orEmpty(),
            powerAndToughness = mapPowerAndToughness(dto.power, dto.toughness)
        )
    }

    fun mapDetailed(dto: MtgCardDto): DetailedMtgCard {
        return DetailedMtgCard(
            id = dto.id.orEmpty(),
            name = dto.name.orEmpty(),
            imageUrl = mapImageUrl(dto.imageUrl.orEmpty()),
            manaCost = dto.manaCost.orEmpty(),
            powerAndToughness = mapPowerAndToughness(dto.power, dto.toughness),
            type = dto.type.orEmpty(),
            artist = dto.artist.orEmpty(),
        )
    }

    private fun mapImageUrl(url: String): String = url.replace("^http://".toRegex(), "https://")

    private fun mapPowerAndToughness(power: Int?, toughness: Int?): String =
        "${power}/${toughness}".takeIf { power != null && toughness != null } ?: ""
}