package com.example.capstone_apps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_apps.data.remote.response.ResponseRegister
import com.example.capstone_apps.repository.RegisterRepository

class RegisterViewModel (private val registerRepository: RegisterRepository): ViewModel() {
  fun registerUser(username: String, email: String, password: String) {
    registerRepository.requestRegister(username, email, password)
  }

  fun getResult(): LiveData<ResponseRegister> =
    registerRepository.result()

  fun getErrorMessage(): LiveData<String> =
    registerRepository.error()

  fun getLoading(): LiveData<Boolean> =
    registerRepository.loading()
}