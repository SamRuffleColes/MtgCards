package es.rufflecol.sam.mtgcards.presentation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {

    data object CardList : Screen("cardList")

    data object CardDetail : Screen(
        route = "cardDetail/{cardId}",
        navArguments = listOf(navArgument("cardId") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(cardId: String) = "cardDetail/${cardId}"
    }

}