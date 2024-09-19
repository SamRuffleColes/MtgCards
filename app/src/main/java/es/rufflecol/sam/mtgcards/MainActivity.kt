package es.rufflecol.sam.mtgcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import es.rufflecol.sam.mtgcards.presentation.MtgApp
import es.rufflecol.sam.mtgcards.presentation.cardlist.CardsListScreen
import es.rufflecol.sam.mtgcards.ui.theme.MtgCardsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MtgCardsTheme {
                MtgApp()
            }
        }
    }
}