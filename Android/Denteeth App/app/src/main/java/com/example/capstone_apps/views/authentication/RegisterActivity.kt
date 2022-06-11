package com.example.capstone_apps.views.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstone_apps.R
import com.example.capstone_apps.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRegisterBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.hide()
  }
}