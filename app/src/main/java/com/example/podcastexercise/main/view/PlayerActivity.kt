package com.example.podcastexercise.main.view

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.podcastexercise.R
import com.example.podcastexercise.model.ContentFeed
import com.example.podcastexercise.utils.ImgLoader
import kotlinx.android.synthetic.main.activity_player.*
import java.util.*

class PlayerActivity : AppCompatActivity() {
    companion object {
        private const val KEY_COVER = "keyCover"
        private const val KEY_DATA = "keyData"
        fun launch(context: Context, cover: String, data: ContentFeed) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(KEY_COVER, cover)
            intent.putExtra(KEY_DATA, data)
            context.startActivity(intent)
        }
    }

    private var currentData: ContentFeed? = null
    private val player by lazy {
        MediaPlayer()
    }

    private val timer by lazy {
        Timer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        initView()
    }

    private fun initView() {

        val coverUrl = intent.getStringExtra(KEY_COVER)
        currentData = intent.getParcelableExtra<ContentFeed>(KEY_DATA)
        coverUrl?.apply {
            ImgLoader.display(this@PlayerActivity, this, iv_cover)
        }

        if (currentData == null) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        } else {
            tv_title.text = currentData!!.title
        }

        iv_play.setOnClickListener{
            if (player.isPlaying) {
                player.pause()
                iv_play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
            } else {
                player.start()
                iv_play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            }
        }

        progress_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val time = p1.toFloat()/100 * player.duration
                player.seekTo(time.toInt())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }

    override fun onResume() {
        super.onResume()
        currentData?.apply {
            player.setDataSource(contentUrl)
            player.setOnPreparedListener {
                totalTime.text = getTimeString(it.duration)

                timer.schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            currentTime.text = getTimeString(player.currentPosition)

                           val pos =  player.currentPosition.toFloat() / player.duration * 100
                            progress_bar.progress = pos.toInt()
                        }
                    }
                }, 500, 1000)

            }
            player.setOnCompletionListener {
                iv_play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)

            }
            player.prepare()
            player.start()
        }
    }

    override fun onPause() {
        timer.cancel()
        player.stop()
        player.release()
        super.onPause()
    }

    private fun getTimeString(millis: Int): String? {
        val buf = StringBuffer()
        val hours = (millis / (1000 * 60 * 60)).toInt()
        val minutes = (millis % (1000 * 60 * 60) / (1000 * 60)).toInt()
        val seconds = (millis % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        if (hours > 0) {
            buf.append(String.format("%02d", hours))
                .append(":")
        }

        if (minutes > 0) {
            buf.append(String.format("%02d", minutes))
                .append(":")
        }

        buf.append(String.format("%02d", seconds))
        return buf.toString()
    }

    fun clickPrevious(v: View) {
        onBackPressed()
    }
}