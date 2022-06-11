package com.example.capstone_apps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_apps.data.remote.response.ResponseLogin
import com.example.capstone_apps.repository.LoginRepository

class LoginViewModel (private val loginRepository: LoginRepository): ViewModel() {
  fun loginUser(email: String, password: String) {
    loginRepository.requestLogin(email, password)
  }

  fun getResult(): LiveData<ResponseLogin> =
    loginRepository.result()

  fun getLoading(): LiveData<Boolean> =
    loginRepository.loading()

  fun getError(): LiveData<String> =
    loginRepository.error()

}