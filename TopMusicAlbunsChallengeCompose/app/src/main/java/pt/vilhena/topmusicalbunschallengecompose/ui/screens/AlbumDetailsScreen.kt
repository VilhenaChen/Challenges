package pt.vilhena.topmusicalbunschallengecompose.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pt.vilhena.topmusicalbunschallengecompose.R
import pt.vilhena.topmusicalbunschallengecompose.data.model.Album
import pt.vilhena.topmusicalbunschallengecompose.data.model.SingleAlbumProvider
import pt.vilhena.topmusicalbunschallengecompose.ui.theme.appleMusicColor
import pt.vilhena.topmusicalbunschallengecompose.ui.theme.spotifyColor
import pt.vilhena.topmusicalbunschallengecompose.ui.theme.youtubeMusicColor
import java.net.URLEncoder
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import pt.vilhena.topmusicalbunschallengecompose.data.viewmodel.TopAlbumViewModel

const val TAG = "AlbumDetailsScreen"

@Composable
fun AlbumDetailsScreen(
    modifier: Modifier = Modifier,
    album: Album,
    viewModel: TopAlbumViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {

    var isFavorite by remember(album) { mutableStateOf(album.isFavorite) }

    AlbumDetailsContent(
        modifier = modifier,
        album = album,
        isFavorite = isFavorite,
        onFavoriteToggle = {
            viewModel.addOrRemoveFavorite(album.copy(isFavorite = isFavorite))
            isFavorite = !isFavorite
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailsContent(
    album: Album,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit = {}
) {
    val context = LocalContext.current
    Log.d(TAG, "Album: $album")

    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ElevatedCard(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
            ) {
                Box(contentAlignment = Alignment.TopEnd) {
                    AsyncImage(
                        model = album.imageUrl,
                        placeholder = painterResource(R.drawable.album_art_placeholder),
                        contentDescription = "Album cover",
                        modifier = Modifier
                            .size(280.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Yellow else Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = album.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = album.artistName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoColumn(label = "Genre", value = album.genre)
                InfoColumn(label = "Released", value = album.releaseDate.take(4))
                InfoColumn(label = "Price", value = album.price)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Listen on",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            StreamingButton("Spotify", spotifyColor) {
                openSearchQuery(context, "https://open.spotify.com/search/", album)
            }
            StreamingButton("Apple Music", appleMusicColor) {
                openSearchQuery(context, "https://music.apple.com/search?term=", album)
            }
            StreamingButton("YouTube Music", youtubeMusicColor) {
                openSearchQuery(context, "https://music.youtube.com/search?q=", album)
            }
        }
    }
}

@Composable
fun InfoColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.outline)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun StreamingButton(text: String, containerColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = Color.White)
    ) {
        Icon(Icons.Default.PlayArrow, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text(text = text)
    }
}

private fun openSearchQuery(context: Context, baseUrl: String, album: Album) {
    val query = URLEncoder.encode("${album.title} ${album.artistName}", "UTF-8")
    context.startActivity(Intent(Intent.ACTION_VIEW, "$baseUrl$query".toUri()))
}

@Preview(showBackground = true)
@Composable
fun AlbumDetailsScreenPreview(@PreviewParameter(SingleAlbumProvider::class) album: Album) {
    AlbumDetailsContent(album = album, isFavorite = false)
}