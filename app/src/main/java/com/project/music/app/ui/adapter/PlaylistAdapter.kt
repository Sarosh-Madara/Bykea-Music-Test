package com.project.music.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.music.app.R
import com.project.music.app.responses.Result
import com.project.music.app.utils.gone
import com.project.music.app.utils.loadImageFromURL
import com.project.music.app.utils.visible

class PlaylistAdapter(
    private val dataSet: List<Result>,
    val context: Context,
    private val onClickItem: (item: Result) -> Unit
) :
    ListAdapter<Result, PlaylistAdapter.ViewHolder>(TrackItemListDiffCallBack()) {

    var previousTrackId = -1
    var previousPosition = -1

    class ViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
        val txtSongName: TextView
        val txtSongArtist: TextView
        val txtSongAlbum: TextView
        val imgIsSongPlaying: ImageView
        val imgAlbumArt: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            txtSongName = view.findViewById(R.id.txt_song_name)
            txtSongArtist = view.findViewById(R.id.txt_song_artist)
            txtSongAlbum = view.findViewById(R.id.txt_song_album)
            imgAlbumArt = view.findViewById(R.id.iv_album_art)
            imgIsSongPlaying = view.findViewById(R.id.iv_song_playing)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_song_item, viewGroup, false)

        return ViewHolder(context, view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.txtSongAlbum.text = dataSet[position].collectionName
        viewHolder.txtSongArtist.text = dataSet[position].artistName
        viewHolder.txtSongName.text = dataSet[position].artistName
        dataSet[position].artworkUrl30.let {
            context.loadImageFromURL(dataSet[position].artworkUrl30, viewHolder.imgAlbumArt)
        }
        if (previousTrackId == dataSet[position].trackId)
            viewHolder.imgIsSongPlaying.visible()
        else
            viewHolder.imgIsSongPlaying.gone()

        viewHolder.itemView.setOnClickListener {

            previousTrackId = dataSet[position].trackId
            notifyItemChanged(previousPosition)
            previousPosition = viewHolder.adapterPosition
            onClickItem(dataSet[position])
            if (previousTrackId == dataSet[position].trackId)
                viewHolder.imgIsSongPlaying.visible()
            else
                viewHolder.imgIsSongPlaying.gone()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    class TrackItemListDiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.trackId == newItem.trackId
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

}
