package com.example.ContentFeedexercise.main.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.podcastexercise.R
import com.example.podcastexercise.model.ContentFeed
import kotlinx.android.synthetic.main.item_content_feed.view.*


class DetailAdapter(
    private val context: Context?,
    private val onItemClicked: (ContentFeed) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = mutableListOf<ContentFeed>()

    fun setData(list: List<ContentFeed>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_content_feed, parent, false)
        return ContentFeedViewHolder(view)

    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContentFeedViewHolder -> {
                holder.bind(dataList[position])
            }
        }
    }

    inner class ContentFeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: ContentFeed) {
            itemView.apply {
                title.text = data.title
                this.setOnClickListener {
                    onItemClicked(data)
                }
            }

        }
    }

}