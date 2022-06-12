package com.example.capstone_apps.views.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_apps.R
import com.example.capstone_apps.databinding.ActivityLoginBinding
import com.example.capstone_apps.viewmodels.LoginViewModel
import com.example.capstone_apps.viewmodels.PreferenceViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory
import com.example.capstone_apps.views.home.HomeActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var binding: ActivityLoginBinding
  private lateinit var loginViewModel: LoginViewModel
  private lateinit var preferenceViewModel: PreferenceViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.hide()

    binding.buttonLogin.setOnClickListener(this)
    binding.buttonRegister.setOnClickListener(this)
    showLoading(false)
  }

  override fun onStart() {
    super.onStart()
    preferenceViewModel = obtainPreferenceViewModel(this)
    if(preferenceViewModel.checkIsLogin()) {
      startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
      finish()
    }
  }

  override fun onClick(view: View) {
    when (view.id) {
      R.id.buttonLogin -> {
        showLoading(true)
        submitForm(
          binding.emailEditText.text.toString(),
          binding.passwordEditText.text.toString()
        )
      }
      R.id.buttonRegister -> {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
      }
    }
  }

  private fun submitForm(email: String, password: String) {
    loginViewModel = obtainLoginViewModel(this)
    preferenceViewModel = obtainPreferenceViewModel(this)
    loginViewModel.loginUser(email, password)
    loginViewModel.getResult().observe(this) {
      it.getContentIfNotHandled()?.let { response ->
        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
        preferenceViewModel.saveToken(response.token)
        preferenceViewModel.saveSessionLogin(true)
        moveToHomeActivity()
      }
    }
    loginViewModel.getError().observe(this) {
      it.getContentIfNotHandled()?.let { snackBarText ->
        Snackbar.make(
          window.decorView.rootView,
          snackBarText,
          Snackbar.LENGTH_SHORT
        ).show()
      }
    }
    loginViewModel.getLoading().observe(this) {
      it.getContentIfNotHandled()?.let { it1 -> showLoading(it1) }
    }
  }


  private fun showLoading(isLoading: Boolean) {
    binding.spinKit.visibility = if(isLoading) View.VISIBLE else View.GONE
  }

  private fun moveToHomeActivity(){
    val intent = Intent(this, HomeActivity::class.java)
    startActivity(intent)
  }

  private fun obtainPreferenceViewModel(activity: AppCompatActivity): PreferenceViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(PreferenceViewModel::class.java)
  }

  private fun obtainLoginViewModel(activity: AppCompatActivity) : LoginViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
  }
}