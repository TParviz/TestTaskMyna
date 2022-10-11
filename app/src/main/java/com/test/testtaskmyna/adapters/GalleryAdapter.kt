package com.test.testtaskmyna.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.testtaskmyna.callbacks.OnItemClickListener
import com.test.testtaskmyna.data.model.MediaModel
import com.test.testtaskmyna.databinding.MediaViewBinding
import com.test.testtaskmyna.ui.viewholders.MediaViewHolder

class GalleryAdapter(
    val context: Activity,
    private val mediaList: List<MediaModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MediaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MediaViewBinding.inflate(inflater, parent, false)
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(context, mediaList[position])
        holder.itemView.setOnClickListener {
            listener.onItemClick(mediaList[position])
        }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }
}