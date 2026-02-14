package pt.vilhena.topmusicalbunschallenge.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import pt.vilhena.topmusicalbunschallenge.R
import pt.vilhena.topmusicalbunschallenge.data.model.Album
import pt.vilhena.topmusicalbunschallenge.databinding.TopalbumlistItemBinding

class AlbunsListAdapter(private val onClick: (Album) -> Unit): RecyclerView.Adapter<AlbunsListAdapter.ViewHolder>() {

    companion object {
        const val TAG = "AlbunsListAdapter"
    }

    private val albums = mutableListOf<Album>()

    class ViewHolder(val binding: TopalbumlistItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = TopalbumlistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // BIND THE LIST HERE

        val album = albums[position]
        Log.d(TAG, "onBindViewHolder: ")

        holder.binding.apply {
            artistName.text = album.artistName
            albumName.text = album.title
            Glide
                .with(holder.itemView)
                .load(album.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.album_art_placeholder)
                .error(R.drawable.album_art_placeholder)
                .into(albumArt)
        }

        holder.binding.root.setOnClickListener {
            onClick(album)
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "ItemCount: ${albums.size}")
        return albums.size
    }

    fun submitList(newAlbums: List<Album>) {
        albums.clear()
        albums.addAll(newAlbums)
        notifyDataSetChanged()
    }

}