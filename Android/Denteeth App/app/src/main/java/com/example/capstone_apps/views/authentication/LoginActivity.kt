package com.example.capstone_apps.views.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_apps.R
import com.example.capstone_apps.databinding.ActivityLoginBinding
import com.example.capstone_apps.viewmodels.LoginViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory

class LoginActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var binding: ActivityLoginBinding
  

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.hide()

    binding.buttonLogin.setOnClickListener(this)
    binding.buttonRegister.setOnClickListener(this)
  }

  override fun onClick(view: View) {
    when (view.id) {
      R.id.buttonLogin -> {

      }
      R.id.buttonRegister -> {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
      }
    }
  }

  private fun obtainLoginViewModel(activity: AppCompatActivity) : LoginViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
  }
}