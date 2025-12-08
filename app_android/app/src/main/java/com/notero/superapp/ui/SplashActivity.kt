package com.notero.superapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.notero.superapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val videoView: VideoView = findViewById(R.id.splashVideo)
        val uri = Uri.parse("android.resource://${packageName}/${R.raw.splash}")
        videoView.setVideoURI(uri)
        videoView.setOnCompletionListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        videoView.start()
    }
}

