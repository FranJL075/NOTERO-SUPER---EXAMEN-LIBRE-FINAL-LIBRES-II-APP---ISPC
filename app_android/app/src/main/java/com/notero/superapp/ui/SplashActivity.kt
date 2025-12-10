package com.notero.superapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.notero.superapp.R
import com.notero.superapp.network.AuthInterceptor
import com.notero.superapp.storage.TokenManager
import com.notero.superapp.JavaUtils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        JavaUtils.debug("SplashActivity started")

        lifecycleScope.launch {
            val tokenManager = TokenManager(applicationContext)
            val token = tokenManager.accessToken.first()
            if (token.isNotBlank()) {
                AuthInterceptor.setToken(token)
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
                return@launch
            }
            // si no hay token, se muestra video y luego Login
            val videoView: VideoView = findViewById(R.id.splashVideo)
            val uri = Uri.parse("android.resource://${packageName}/${R.raw.splash}")
            videoView.setVideoURI(uri)
            videoView.setOnCompletionListener {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
            videoView.start()
        }
    }
}

