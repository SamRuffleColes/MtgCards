package es.rufflecol.sam.mtgcards.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.rufflecol.sam.mtgcards.presentation.carddetail.CardDetailScreen
import es.rufflecol.sam.mtgcards.presentation.cardlist.CardsListScreen

@Composable
fun MtgApp() {
    val navController = rememberNavController()
    MtgNavHost(navController = navController)
}

@Composable
fun MtgNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.CardList.route) {
        composable(route = Screen.CardList.route) {
            CardsListScreen(
                onNavigateToCardDetail = {
                    navController.navigate(Screen.CardDetail.createRoute(it))
                }
            )
        }
        composable(
            route = Screen.CardDetail.route,
            arguments = Screen.CardDetail.navArguments,
        ) {
            CardDetailScreen()
        }
    }
}