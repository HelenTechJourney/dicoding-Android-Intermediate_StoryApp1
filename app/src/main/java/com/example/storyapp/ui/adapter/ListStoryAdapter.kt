package com.example.storyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.remote.response.ListStoryItem


class ListStoryAdapter(private val listStory: List<ListStoryItem>) :
RecyclerView.Adapter<ListStoryAdapter.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.img_story)
        var tvName: TextView = view.findViewById(R.id.tv_name)
    }

    private var onItemClickCallback: OnItemClickCallback?=null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_story, viewGroup, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listStory[position]
        holder.tvName.text = data.name
        Glide.with(holder.itemView)
            .load(data.photoUrl)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listStory[holder.adapterPosition])
        }
    }

    override fun getItemCount() = listStory.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }
}