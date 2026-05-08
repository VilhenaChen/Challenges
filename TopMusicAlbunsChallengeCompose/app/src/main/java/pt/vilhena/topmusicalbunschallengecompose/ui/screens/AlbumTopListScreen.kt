package pt.vilhena.topmusicalbunschallengecompose.ui.screens

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import pt.vilhena.topmusicalbunschallengecompose.R
import pt.vilhena.topmusicalbunschallengecompose.data.MultipleAlbumProvider
import pt.vilhena.topmusicalbunschallengecompose.data.SingleAlbumProvider
import pt.vilhena.topmusicalbunschallengecompose.data.dto.NetworkResult
import pt.vilhena.topmusicalbunschallengecompose.data.model.Album
import pt.vilhena.topmusicalbunschallengecompose.data.viewmodel.TopAlbumViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopListScreen(
    modifier: Modifier,
    viewModel: TopAlbumViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    onAlbumClick: (Album) -> Unit = {}
) {

    val uiState: NetworkResult<List<Album>> by viewModel.albumsState.collectAsStateWithLifecycle()

    Surface(modifier = modifier) {
        when (uiState) {
            is NetworkResult.Loading -> {
                // Loading
                LoadingScreen()
            }

            is NetworkResult.Success -> {
                // Success
                AlbumListSuccess((uiState as NetworkResult.Success<List<Album>>).data, onAlbumClick, viewModel)
            }

            is NetworkResult.Error -> {
                // Error
                ErrorScreen {
                    // On Retry clicked
                    viewModel.getTopAlbumsList()
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumList(
    @PreviewParameter(MultipleAlbumProvider::class) albumList: List<Album>,
    onAlbumClick: (Album) -> Unit = {}
) {
    Log.d(TAG, albumList.toString())

    Surface {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.FixedSize(150.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(albumList.size) { index ->
                AlbumItem(album = albumList[index], onClick = { onAlbumClick(albumList[index]) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumItem(
    @PreviewParameter(SingleAlbumProvider::class) album: Album,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(size = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
            AsyncImage(
                model = album.imageUrl,
                placeholder = painterResource(R.drawable.album_art_placeholder),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )
            Column (modifier = Modifier.padding(4.dp)) {
                Text(
                    text = album.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = album.artistName,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AlbumListSuccess(albumList: List<Album>, onAlbumClick: (Album) -> Unit, viewModel: TopAlbumViewModel) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {

        AlbumList(
            albumList,
            onAlbumClick
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            FloatingSelectableButton(
                label = stringResource(R.string.top),
                isSelected = selectedIndex == 0,
                onClick = {
                    Log.d(TAG, "On top button clicked")
                    selectedIndex = 0
                    viewModel.orderListToTop()
                }
            )


            FloatingSelectableButton(
                label = stringResource(R.string.alphabetically),
                isSelected = selectedIndex == 1,
                onClick = {
                    Log.d(TAG, "On Alphabetically button clicked")
                    selectedIndex = 1
                    viewModel.orderListAlphabetically()
                }
            )

            FloatingSelectableButton(
                label = stringResource(R.string.favorites),
                isSelected = selectedIndex == 2,
                onClick = {
                    Log.d(TAG, "On favorites button clicked")
                    selectedIndex = 2
                    viewModel.showOnlyFavorites()
                }
            )
        }
    }
}

@Composable
fun FloatingSelectableButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text = label)
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingScreen() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator(modifier = Modifier.size(125.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun ErrorScreen(onClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = stringResource(R.string.error),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge)
        Text(text = stringResource(R.string.error_description),
            style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error, contentColor = Color.White)
        ) {
            Text(text = stringResource(R.string.retry_btn))
        }
    }
}
