package com.stefanini.imgurcatfetcher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stefanini.imgurcatfetcher.R
import com.stefanini.imgurcatfetcher.model.ImageDto

class MyRecyclerViewAdapter (private val dataSet: MutableList<ImageDto>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.rv_item_imgview)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.imageView.context)
            .load(dataSet[position].link)
            .into(viewHolder.imageView);
    }

    override fun getItemCount() = dataSet.size

}