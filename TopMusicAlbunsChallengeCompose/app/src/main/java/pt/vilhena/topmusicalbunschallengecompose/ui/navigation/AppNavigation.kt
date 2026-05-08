package pt.vilhena.topmusicalbunschallengecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import pt.vilhena.topmusicalbunschallengecompose.data.model.Album
import pt.vilhena.topmusicalbunschallengecompose.ui.screens.AlbumDetailsScreen
import pt.vilhena.topmusicalbunschallengecompose.ui.screens.AlbumTopListScreen
import kotlin.reflect.typeOf

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = AlbumTopListRoute
    ) {
        composable<AlbumTopListRoute> {
            AlbumTopListScreen(
                modifier = modifier,
                onAlbumClick = { album ->
                    navController.navigate(AlbumDetailsRoute(album = album))
                }
            )
        }

        composable<AlbumDetailsRoute>(
            typeMap = mapOf(typeOf<Album>() to Album.NavigationType)
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<AlbumDetailsRoute>()
            AlbumDetailsScreen(
                modifier = modifier,
                album = route.album
            )
        }
    }
}
