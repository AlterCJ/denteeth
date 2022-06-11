package com.example.capstone_apps.views.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.capstone_apps.databinding.ActivitySplashBinding
import com.example.capstone_apps.helper.Key
import com.example.capstone_apps.views.authentication.LoginActivity

class SplashActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySplashBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySplashBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.hide()

    handlerSplash()
  }

  private fun handlerSplash(): Boolean {
    return Handler(Looper.getMainLooper()).postDelayed({
      startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
      finish()
    }, Key.TIME)
  }
}