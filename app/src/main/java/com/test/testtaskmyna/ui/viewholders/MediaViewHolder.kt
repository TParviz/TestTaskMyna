package com.test.testtaskmyna.ui.viewholders

import android.content.Context
import android.net.Uri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.testtaskmyna.data.model.MediaModel
import com.test.testtaskmyna.databinding.MediaViewBinding
import com.test.testtaskmyna.utilities.Constants
import java.io.File

class MediaViewHolder(private val itemBinding: MediaViewBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(context: Context, directoryModel: MediaModel) {
        val photoUri = Uri.fromFile(File(directoryModel.thumbUri))
        Glide.with(context).load(photoUri).centerCrop().into(itemBinding.thumbView)
        itemBinding.mediaType.isVisible = photoUri.toString().endsWith(Constants.VIDEO_TYPE)
    }
}