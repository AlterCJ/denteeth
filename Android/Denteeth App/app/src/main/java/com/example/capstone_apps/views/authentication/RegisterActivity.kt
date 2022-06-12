package com.example.capstone_apps.views.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_apps.R
import com.example.capstone_apps.databinding.ActivityRegisterBinding
import com.example.capstone_apps.viewmodels.RegisterViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRegisterBinding
  private lateinit var registerViewModel: RegisterViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.hide()

    showLoading(false)

    binding.buttonRegister.setOnClickListener { submitForm() }
  }

  private fun submitForm() {
    showLoading(true)
    submitFormRegister(
      binding.usernameEditText.text.toString(),
      binding.emailEditText.text.toString(),
      binding.passwordEditText.text.toString()
    )
  }


  private fun submitFormRegister(username: String, email: String, password: String) {
    registerViewModel = obtainViewModel(this)
    registerViewModel.registerUser(username, email, password)
    registerViewModel.getResult().observe(this) {
      makeToast(it.message)
      moveToLoginActivity()
    }
    registerViewModel.getErrorMessage().observe(this) {
      makeToast("failed register $it")
    }
    registerViewModel.getLoading().observe(this){
      showLoading(it)
    }
  }

  private fun moveToLoginActivity() {
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
  }

  private fun obtainViewModel(activity: AppCompatActivity): RegisterViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(RegisterViewModel::class.java)
  }

  private fun showLoading(isLoading: Boolean) {
    binding.spinKitRegister.visibility = if(isLoading) View.VISIBLE else View.GONE
  }

  private fun makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }
}