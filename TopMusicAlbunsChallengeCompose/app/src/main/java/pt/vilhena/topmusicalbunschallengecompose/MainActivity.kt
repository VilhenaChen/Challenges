package pt.vilhena.topmusicalbunschallengecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pt.vilhena.topmusicalbunschallengecompose.data.viewmodel.TopAlbumViewModel
import pt.vilhena.topmusicalbunschallengecompose.ui.navigation.AppNavigation
import pt.vilhena.topmusicalbunschallengecompose.ui.theme.TopMusicAlbunsChallengeComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TopMusicAlbunsChallengeComposeTheme {

                val navController =
                    rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding), navController)
                }
            }
        }
    }
}