package com.test.testtaskmyna.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.testtaskmyna.callbacks.OnItemClickListener
import com.test.testtaskmyna.data.model.DirectoryModel
import com.test.testtaskmyna.databinding.DirectoryViewLayoutBinding
import com.test.testtaskmyna.ui.viewholders.DirectoryViewHolder

class DirectoryAdapter(
    val context: Activity,
    private val directoryHashMap: HashMap<String, MutableList<DirectoryModel>>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<DirectoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DirectoryViewLayoutBinding.inflate(inflater, parent, false)
        return DirectoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DirectoryViewHolder, position: Int) {
        val directoryName = directoryHashMap.keys.sorted().elementAt(position)
        val list = directoryHashMap[directoryName]
        if (list != null) {
            val directoryModel = list[0]
            holder.bind(context, directoryModel, list.size)
            holder.itemView.setOnClickListener {
                listener.onItemClick(directoryModel)
            }
        }
    }

    override fun getItemCount(): Int {
        return directoryHashMap.size
    }
}