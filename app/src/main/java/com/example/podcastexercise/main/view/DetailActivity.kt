package com.example.podcastexercise.main.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ContentFeedexercise.main.view.DetailAdapter
import com.example.podcastexercise.R
import com.example.podcastexercise.main.architecture.PodCastViewModel
import com.example.podcastexercise.model.CastCollection
import com.example.podcastexercise.utils.ImgLoader
import com.example.podcastexercise.utils.observeNonNull
import com.example.podcastexercise.utils.observeVisible
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        fun launch(context: Context, id: Int) {
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var podCast: CastCollection? = null
    private val viewModel by viewModel<PodCastViewModel>()
    private val adapter by lazy {
        DetailAdapter(this) {
            podCast?.artworkUrl600?.apply {
                PlayerActivity.launch(
                    this@DetailActivity,
                    this, it)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initView()
        observeData()
        viewModel.getCastDetail()
    }


    fun clickPrevious(v: View) {
        onBackPressed()
    }

    private fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        val decoration = ItemDecoration(
            this,
            Color.parseColor("#a8a8a8"),
            0f,
            1f
        )
        recycler_view.addItemDecoration(decoration)
        recycler_view.adapter = adapter
    }

    private fun observeData() {
        viewModel.apply {
            observeNonNull(castDetail) {
                ImgLoader.display(this@DetailActivity, it.artworkUrl100, cover)
                name.text = it.collectionName
                desc.text = it.artistName
                podCast = it
                it.contentFeed?.apply {
                    adapter.setData(it.contentFeed)
                }
            }

            observeVisible(isLoading, progress_bar)

            observeNonNull(error) {
                Toast.makeText(this@DetailActivity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}