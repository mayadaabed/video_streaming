package com.example.videostreaming

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mediaControls: MediaController? = null
    lateinit var expoPlayer: SimpleExoPlayer
    var videoUrl :String = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    var playWhenReady: Boolean =true
    var currentWindow: Int =0
    var playpackPosition: Long =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun initVideo(){
        expoPlayer = ExoPlayerFactory.newSimpleInstance(this)
        videoView.player = expoPlayer
        val uri: Uri = Uri.parse(videoUrl)
        val dataSource: DataSource.Factory  = DefaultDataSourceFactory(this, "exoplayer-codelab")
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSource).createMediaSource(uri)

        expoPlayer.playWhenReady = playWhenReady
        expoPlayer.seekTo(currentWindow, playpackPosition)
        expoPlayer.prepare(mediaSource, false, false)

    }

    fun releaseVideo(){
        if(expoPlayer != null){
            playWhenReady = expoPlayer.playWhenReady
            playpackPosition = expoPlayer.currentPosition
            currentWindow = expoPlayer.currentWindowIndex
            expoPlayer.release()
            expoPlayer == null
        }
    }

    override fun onStart() {
        super.onStart()
        initVideo()
    }

    override fun onResume() {
        super.onResume()
        if(expoPlayer != null){
            initVideo()
        }
    }

    override fun onStop() {
        super.onStop()
        releaseVideo()
    }

    override fun onPause() {
        super.onPause()
        releaseVideo()
    }
}