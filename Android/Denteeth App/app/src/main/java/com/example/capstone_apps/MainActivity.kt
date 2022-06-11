package com.example.capstone_apps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_apps.helper.Key
import com.example.capstone_apps.helper.ResultResponse
import com.example.capstone_apps.viewmodels.*
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
  private lateinit var loginViewModel: LoginViewModel
  private lateinit var registerViewModel: RegisterViewModel
  private lateinit var articleViewModel: ArticleViewModel
  private lateinit var profileViewModel: ProfileViewModel
  private lateinit var bennersViewModel: BennersViewModel

 private var id: Int = 2
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

  }

  private fun testBennerViewModel() {
    bennersViewModel = obtainBennerViewModel(this)
    bennersViewModel.getAllBenners().observe(this) { result ->
      if(result != null) {
        when(result) {
          is ResultResponse.Loading -> {

          }
          is ResultResponse.Success -> {
            Log.d("MainActivity", "data list <<< ${result.data}")
          }
          is ResultResponse.Error -> {
            Log.d("MainActivity", "data error <<< ${result.error}")
          }
        }
      }
    }
  }

  private fun testUpdateProfile() {
    profileViewModel = obtainProfileViewModel(this)
    profileViewModel.updateProfile(Key.token,"uploads/2022-05-24T23:05:09.651Z_pexels-daniel-xavier-1239291.jpg", "testing", "2022-05-18T01:18:44.000Z", "jakarta", "pria")
    profileViewModel.getResult().observe(this) {
      Log.d("MainActivity", "data profile <<< $it")
    }
    profileViewModel.getErrorMessage().observe(this) {
      Log.d("MainActivity", "error <<< $it")
    }
  }

  private fun testGetDetailProfile() {
    profileViewModel = obtainProfileViewModel(this)
    profileViewModel.getDetailProfile(Key.token, id)
    profileViewModel.getResult().observe(this) {
      Log.d("MainActivity", "data profile <<< $it")
    }
    profileViewModel.getErrorMessage().observe(this) {
      Log.d("MainActivity", "error <<< $it")
    }
  }

  private fun testListArticle() {
    articleViewModel = obtainArticleViewModel(this)
    articleViewModel.getAllListArticle().observe(this) {
      if(it != null) {
        when(it) {
          is ResultResponse.Loading -> {

          }
          is ResultResponse.Success -> {
            Log.d("MainActivity", "data list <<< $it")
          }
          is ResultResponse.Error -> {
            Log.d("MainActivity", "data error <<< $it")
          }
        }
      }
    }
  }

  private fun testRegister() {
    registerViewModel = obtainRegisterViewModel(this)
    registerViewModel.registerUser("test", "test566@gmail.com", "test12345")
    registerViewModel.getResult().observe(this) {
      Log.d("MainActivity", "data register <<< $it")
    }
    registerViewModel.getErrorMessage().observe(this) {
      Log.d("MainActivity", "data error <<< $it")
    }
  }

  private fun testLogin() {
    loginViewModel = obtainViewModel(this)
    loginViewModel.loginUser("test12@gmail.com", "@Test1234")
    loginViewModel.getResult().observe(this) {
      Log.d("MainActivity", "data login <<< $it")
    }
    loginViewModel.getError().observe(this) {
      Log.e("MainActivity", "error login << $it")
    }
  }

  private fun obtainBennerViewModel(activity: AppCompatActivity): BennersViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(BennersViewModel::class.java)
  }

  private fun obtainProfileViewModel(activity: AppCompatActivity): ProfileViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(ProfileViewModel::class.java)
  }

  private fun obtainArticleViewModel(activity: AppCompatActivity): ArticleViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(ArticleViewModel::class.java)
  }

  private fun obtainRegisterViewModel(activity: AppCompatActivity): RegisterViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(RegisterViewModel::class.java)
  }

  private fun obtainViewModel(activity: AppCompatActivity): LoginViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
  }
}