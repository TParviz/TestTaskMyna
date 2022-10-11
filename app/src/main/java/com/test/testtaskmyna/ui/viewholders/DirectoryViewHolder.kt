package com.test.testtaskmyna.ui.viewholders

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.testtaskmyna.R
import com.test.testtaskmyna.data.model.DirectoryModel
import com.test.testtaskmyna.databinding.DirectoryViewLayoutBinding
import com.test.testtaskmyna.utilities.Constants
import java.io.File

class DirectoryViewHolder(private val itemBinding: DirectoryViewLayoutBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(context: Context, directoryModel: DirectoryModel, count: Int) {
        itemBinding.directoryNameTV.text = directoryModel.name
        itemBinding.imageCountTV.text = count.toString()
        when (directoryModel.name) {
            Constants.IMAGES -> itemBinding.directoryNameTV.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_image_24, 0, 0, 0
            )
            Constants.VIDEOS -> itemBinding.directoryNameTV.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_videocam_24, 0, 0, 0
            )
            Constants.CAMERA -> itemBinding.directoryNameTV.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_camera_alt_24, 0, 0, 0
            )
            else -> itemBinding.directoryNameTV.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_folder_open_24, 0, 0, 0
            )
        }

        val photoUri = Uri.fromFile(File(directoryModel.thumbUri))
        Glide.with(context).load(photoUri).centerCrop().into(itemBinding.directoryThumbIV)
    }
}