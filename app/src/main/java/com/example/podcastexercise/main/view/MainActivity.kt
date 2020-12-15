package com.example.podcastexercise.main.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.podcastexercise.R
import com.example.podcastexercise.main.architecture.PodCastViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.podcastexercise.utils.observeNonNull
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<PodCastViewModel>()

    private val adapter by lazy {
        MainAdapter(this) {
            DetailActivity.launch(this, it.id)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        observeData()
        viewModel.getCasts()
    }

    private fun initView() {

        swipe.setOnRefreshListener {
            viewModel.getCasts()
        }

        val layoutManager = GridLayoutManager(this, 2)
        val decoration = ItemDecoration(
            this,
            Color.parseColor("#000000"),
            10f,
            10f
        )

        recycler_view.layoutManager = layoutManager
        recycler_view.addItemDecoration(decoration)
        recycler_view.adapter = adapter
    }

    private fun observeData() {
        viewModel.apply {

            observeNonNull(castsList) {
                adapter.setData(it)
            }

            observeNonNull(isLoading) {
                swipe.isRefreshing = it
            }
            observeNonNull(error) {
                Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
