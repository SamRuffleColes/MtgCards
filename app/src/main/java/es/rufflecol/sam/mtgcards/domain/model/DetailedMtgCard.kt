package es.rufflecol.sam.mtgcards.domain.model

data class DetailedMtgCard(
    val id: String,
    val name: String,
    val manaCost: String,
    val imageUrl: String,
    val powerAndToughness: String,
    val type: String,
    val artist: String,
) {
    companion object {
        val Empty = DetailedMtgCard(
            id = "",
            name = "",
            manaCost = "",
            imageUrl = "",
            powerAndToughness = "",
            type = "",
            artist = "",
        )
    }
}
