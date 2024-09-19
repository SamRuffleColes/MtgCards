package es.rufflecol.sam.mtgcards.domain.model

data class SimpleMtgCard(
    val id: String,
    val name: String,
    val manaCost: String,
    val imageUrl: String,
    val powerAndToughness: String,
) {
    companion object {
        val Empty = SimpleMtgCard(
            id = "",
            name = "",
            manaCost = "",
            imageUrl = "",
            powerAndToughness = "",
        )
    }
}
