package com.example.podcastexercise.main.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.podcastexercise.R
import com.example.podcastexercise.model.PodCast
import com.example.podcastexercise.utils.ImgLoader
import kotlinx.android.synthetic.main.item_podcast.view.*


class MainAdapter(
    private val context: Context?,
    private val onItemClicked: (PodCast) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = mutableListOf<PodCast>()

    fun setData(list: List<PodCast>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_podcast, parent, false)
        return CastViewHolder(view)

    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CastViewHolder -> {
                holder.bind(dataList[position])
            }
        }
    }

    inner class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: PodCast) {
            itemView.apply {
                name.text = data.artistName
                desc.text = data.name
                ImgLoader.display(context, data.artworkUrl100, cover)

                this.setOnClickListener {
                    onItemClicked(data)
                }
            }

        }
    }

}