package pt.vilhena.topmusicalbunschallenge.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import pt.vilhena.topmusicalbunschallenge.TopAlbumApplication
import pt.vilhena.topmusicalbunschallenge.data.viewmodel.TopAlbumViewModel
import pt.vilhena.topmusicalbunschallenge.databinding.FragmentAlbumDetailedInformationBinding
import java.net.URLEncoder
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AlbumDetailedInformationFragment : Fragment() {

    @Inject
    lateinit var viewModel: TopAlbumViewModel

    private lateinit var binding: FragmentAlbumDetailedInformationBinding

    private val args: AlbumDetailedInformationFragmentArgs by navArgs()

    companion object {
        const val TAG = "AlbumDetailedInformationFragment"
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as TopAlbumApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView")
        binding = FragmentAlbumDetailedInformationBinding.inflate(inflater, container, false)

        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
        val zonedDate = ZonedDateTime.parse(args.album.releaseDate)
        val formattedDate = zonedDate.format(formatter)

        binding.apply {
            Glide.with(requireContext())
            .load(args.album.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(albumArt)
            artistName.text = args.album.artistName
            albumName.text = args.album.title
            releaseDate.text =  formattedDate
            genre.text = args.album.genre
            price.text = args.album.price

            // open music players
            spotifyBtn.setOnClickListener {
                Log.d(TAG, "Spotify button clicked")
                openSpotify()
            }
            appleMusicBtn.setOnClickListener {
                Log.d(TAG, "Apple Music button clicked")
                openAppleMusic()
            }
            youtubeMusicBtn.setOnClickListener {
                Log.d(TAG, "YouTube Music button clicked")
                openYouTubeMusic()
            }
        }


        return binding.root
    }

    private fun openSpotify() {
        val albumName = args.album.title
        val artistName = args.album.artistName
        val query = URLEncoder.encode("$albumName $artistName", "UTF-8")
        val spotifyUrl = "https://open.spotify.com/search/$query"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(spotifyUrl))

        startActivity(intent) // Will open app if installed, otherwise browser
    }

    private fun openAppleMusic() {
        val albumName = args.album.title
        val artistName = args.album.artistName
        val query = URLEncoder.encode("$albumName $artistName", "UTF-8")
        val appleMusicUrl = "https://music.apple.com/search?term=$query"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appleMusicUrl))

        startActivity(intent)
    }

    private fun openYouTubeMusic() {
        val albumName = args.album.title
        val artistName = args.album.artistName
        val query = URLEncoder.encode("$albumName $artistName", "UTF-8")
        val ytMusicUrl = "https://music.youtube.com/search?q=$query"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ytMusicUrl))

        startActivity(intent)
    }
}