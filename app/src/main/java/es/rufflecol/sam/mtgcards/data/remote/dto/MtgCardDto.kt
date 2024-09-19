package es.rufflecol.sam.mtgcards.data.remote.dto

data class MtgCardDto(
    val id: String?,
    val name: String?,
    val text: String?,
    val manaCost: String?,
    val imageUrl: String?,
    val power: Int?,
    val toughness: Int?,
    val type: String?,
    val artist: String?,
)
